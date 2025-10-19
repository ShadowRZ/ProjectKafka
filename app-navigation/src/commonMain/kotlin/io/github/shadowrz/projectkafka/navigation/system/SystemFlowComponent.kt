package io.github.shadowrz.projectkafka.navigation.system

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.features.about.api.AboutEntryPoint
import io.github.shadowrz.projectkafka.features.editmember.api.AddMemberEntryPoint
import io.github.shadowrz.projectkafka.features.ftue.api.FtueEntryPoint
import io.github.shadowrz.projectkafka.features.ftue.api.FtueService
import io.github.shadowrz.projectkafka.features.ftue.api.FtueState
import io.github.shadowrz.projectkafka.features.home.api.HomeEntryPoint
import io.github.shadowrz.projectkafka.features.licenses.api.LicenseEntryPoint
import io.github.shadowrz.projectkafka.features.share.api.ShareData
import io.github.shadowrz.projectkafka.features.share.api.ShareEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.HasBackHandler
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.navigation.intent.ResolvedIntent
import io.github.shadowrz.projectkafka.navigation.maybeReplaceAll
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable

@AssistedInject
class SystemFlowComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    coroutineDispatchers: CoroutineDispatchers,
    private val ftueService: FtueService,
    private val ftueEntryPoint: FtueEntryPoint,
    private val homeEntryPoint: HomeEntryPoint,
    private val aboutEntryPoint: AboutEntryPoint,
    private val licenseEntryPoint: LicenseEntryPoint,
    private val addMemberEntryPoint: AddMemberEntryPoint,
    private val shareEntryPoint: ShareEntryPoint,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    Resolver<SystemFlowComponent.NavTarget, SystemFlowComponent.Resolved>,
    HasBackHandler {
    init {
        lifecycle.doOnCreate {
            ftueService.state
                .onEach {
                    when (it) {
                        FtueState.Unknown -> Unit
                        FtueState.Incomplete -> {
                            navigation.maybeReplaceAll(NavTarget.Ftue)
                            readyCallback.ready()
                        }
                        FtueState.Complete -> {
                            navigation.maybeReplaceAll(NavTarget.Home)
                            readyCallback.ready()
                        }
                    }
                }.launchIn(lifecycleScope)
        }
    }

    private val lifecycleScope = coroutineScope(coroutineDispatchers.main)

    private val readyCallback = plugin<ReadyCallback>()

    private val navigation = StackNavigation<NavTarget>()

    val childStack: Value<ChildStack<NavTarget, Resolved>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.Placeholder,
            handleBackButton = true,
            childFactory = ::resolve,
        )

    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved =
        when (navTarget) {
            NavTarget.Placeholder -> Resolved.Placeholder
            NavTarget.Ftue -> {
                Resolved.Ftue(
                    ftueEntryPoint.build(
                        parent = this,
                        context = componentContext,
                    ),
                )
            }
            NavTarget.Home -> {
                readyCallback.ready()

                val callback =
                    object : HomeEntryPoint.Callback {
                        override fun onAbout() {
                            navigation.pushNew(NavTarget.About)
                        }

                        override fun onAddMember() {
                            navigation.pushNew(NavTarget.AddMember)
                        }
                    }
                Resolved.Home(
                    homeEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        callback = callback,
                    ),
                )
            }

            NavTarget.About -> {
                val callback =
                    object : AboutEntryPoint.Callback {
                        override fun onLicenses() {
                            navigation.pushNew(NavTarget.Licenses)
                        }
                    }
                Resolved.About(
                    aboutEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        callback = callback,
                    ),
                )
            }

            NavTarget.Licenses ->
                Resolved.Licenses(
                    licenseEntryPoint.build(
                        parent = this,
                        context = componentContext,
                    ),
                )

            NavTarget.AddMember ->
                Resolved.AddMember(
                    addMemberEntryPoint.build(
                        parent = this,
                        context = componentContext,
                    ),
                )
            is NavTarget.Share ->
                Resolved.Share(
                    shareEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        shareData = navTarget.shareData,
                    ),
                )
        }

    override fun onBack() {
        navigation.pop()
    }

    suspend fun onIncomingShare(incomingShare: ResolvedIntent.IncomingShare) {
        childStack.waitForChildAttached { navTarget ->
            navTarget == NavTarget.Home
        }
        navigation.pushNew(NavTarget.Share(incomingShare.shareData))
    }

    @Serializable
    sealed interface NavTarget {
        @Serializable
        data object Placeholder : NavTarget

        @Serializable
        data object Ftue : NavTarget

        @Serializable
        data object Home : NavTarget

        @Serializable
        data object About : NavTarget

        @Serializable
        data object AddMember : NavTarget

        @Serializable
        data object Licenses : NavTarget

        @Serializable
        data class Share(
            val shareData: ShareData,
        ) : NavTarget
    }

    sealed interface Resolved {
        data object Placeholder : Resolved

        data class Ftue(
            val component: Component,
        ) : Resolved

        data class Home(
            val component: Component,
        ) : Resolved

        data class About(
            val component: Component,
        ) : Resolved

        data class AddMember(
            val component: Component,
        ) : Resolved

        data class Licenses(
            val component: Component,
        ) : Resolved

        data class Share(
            val component: Component,
        ) : Resolved
    }

    @ContributesIntoMap(
        SystemScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(SystemFlowComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<SystemFlowComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): SystemFlowComponent
    }
}
