package io.github.shadowrz.projectkafka.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.navigation.waitForChildAttached
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsCache
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import io.github.shadowrz.projectkafka.navigation.intent.ResolvedIntent
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@AssistedInject
@HanekokoroInject(AppScope::class)
class RootFlowComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    private val systemsCache: SystemsCache,
    private val systemsStore: SystemsStore,
    coroutineDispatchers: CoroutineDispatchers,
) : Component(
        context = context,
        plugins = plugins,
    ),
    Resolver<RootFlowComponent.NavTarget, RootFlowComponent.Resolved> {
    private val lifecycleScope =
        coroutineScope(
            context = coroutineDispatchers.main,
        )

    init {
        lifecycle.doOnCreate {
            systemsStore
                .lastSystemID()
                .distinctUntilChanged()
                .onEach { systemID ->
                    if (systemID != null) {
                        systemsCache.get(systemID)
                        navigation.maybeReplaceAll(NavTarget.SystemFlow(systemID))
                    } else {
                        navigation.maybeReplaceAll(NavTarget.NoSystemFlow)
                    }
                }.launchIn(lifecycleScope)
        }
    }

    private val logger = LoggerTag.Root
    private val navigation = StackNavigation<NavTarget>()

    private val readyCallback = plugin<ReadyCallback>()

    val childStack: Value<ChildStack<*, Resolved>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.SplashScreen,
            handleBackButton = false,
            childFactory = ::resolve,
        )

    fun handleIntent(resolvedIntent: ResolvedIntent) {
        lifecycleScope.launch {
            when (resolvedIntent) {
                is ResolvedIntent.IncomingShare -> {
                    onIncomingShare(resolvedIntent)
                }
            }
        }
    }

    suspend fun onIncomingShare(incomingShare: ResolvedIntent.IncomingShare) {
        childStack
            .waitForChildAttached<Resolved.SystemFlow>()
            .component.component
            .onIncomingShare(incomingShare)
    }

    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved =
        when (navTarget) {
            NavTarget.SplashScreen -> {
                Resolved.SplashScreen
            }

            NavTarget.NoSystemFlow -> {
                val callback =
                    object : NoSystemFlowComponent.Callback {
                        @OptIn(ExperimentalTime::class)
                        override fun onFirstSystemCreated(id: SystemID) {
                            lifecycleScope.launch {
                                systemsStore.updateSystemLastUsed(id, Clock.System.now())
                            }
                        }
                    }
                Resolved.NoSystemFlow(
                    childComponent<NoSystemFlowComponent>(
                        componentContext,
                        plugins = listOf(callback, readyCallback),
                    ),
                )
            }

            is NavTarget.SystemFlow -> {
                val system =
                    systemsCache.getOrNull(navTarget.id) ?: return Resolved.SplashScreen.also {
                        // Timber.tag(logger.value).w("Didn't found this session, going to SplashScreen")
                        navigation.replaceAll(NavTarget.SplashScreen)
                        lifecycleScope.launch {
                            systemsCache.get(navTarget.id)
                            navigation.replaceAll(NavTarget.SystemFlow(navTarget.id))
                        }
                    }

                val params = SystemFlowAppScopeComponent.Params(system)

                Resolved.SystemFlow(
                    childComponent<SystemFlowAppScopeComponent>(
                        componentContext,
                        plugins = listOf(params, readyCallback),
                    ),
                )
            }
        }

//    override fun onBack() {
//        navigation.pop()
//    }

    @Serializable
    sealed interface NavTarget {
        @Serializable
        data object SplashScreen : NavTarget

        @Serializable
        data object NoSystemFlow : NavTarget

        @Serializable
        data class SystemFlow(
            val id: SystemID,
        ) : NavTarget
    }

    sealed interface Resolved {
        data object SplashScreen : Resolved

        data class NoSystemFlow(
            val component: NoSystemFlowComponent,
        ) : Resolved

        data class SystemFlow(
            val component: SystemFlowAppScopeComponent,
        ) : Resolved
    }
}
