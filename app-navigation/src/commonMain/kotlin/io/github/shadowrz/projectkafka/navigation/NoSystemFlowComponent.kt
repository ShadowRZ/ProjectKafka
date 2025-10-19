package io.github.shadowrz.projectkafka.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemEntryPoint
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.HasBackHandler
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugins
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.serialization.Serializable

@AssistedInject
class NoSystemFlowComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    private val welcomeEntryPoint: WelcomeEntryPoint,
    private val createSystemEntryPoint: CreateSystemEntryPoint,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    Resolver<NoSystemFlowComponent.NavTarget, NoSystemFlowComponent.Resolved>,
    HasBackHandler {
    interface Callback : Plugin {
        fun onFirstSystemCreated(id: SystemID)
    }

    private val navigation = StackNavigation<NavTarget>()
    private val readyCallback = plugin<ReadyCallback>()

    init {
        lifecycle.doOnCreate {
            readyCallback.ready()
        }
    }

    val childStack: Value<ChildStack<*, Resolved>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.Welcome,
            handleBackButton = true,
            childFactory = ::resolve,
        )

    private val callback = plugins<Callback>().first()

    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved =
        when (navTarget) {
            NavTarget.Welcome ->
                Resolved.Welcome(
                    welcomeEntryPoint.build(
                        this,
                        componentContext,
                        object : WelcomeEntryPoint.Callback {
                            override fun onCreateSystem() {
                                navigation.pushNew(NavTarget.CreateSystem)
                            }

                            override fun onLearnMore() {
                                //
                            }
                        },
                    ),
                )

            NavTarget.CreateSystem ->
                Resolved.CreateSystem(
                    createSystemEntryPoint.build(
                        this,
                        componentContext,
                        object : CreateSystemEntryPoint.Callback {
                            override fun onFinished(id: SystemID) {
                                callback.onFirstSystemCreated(id)
                            }
                        },
                    ),
                )
        }

    override fun onBack() {
        navigation.pop()
    }

    @Serializable
    sealed interface NavTarget {
        @Serializable
        data object Welcome : NavTarget

        @Serializable
        data object CreateSystem : NavTarget
    }

    sealed interface Resolved {
        data class Welcome(
            val component: Component,
        ) : Resolved

        data class CreateSystem(
            val component: Component,
        ) : Resolved
    }

    @ContributesIntoMap(
        AppScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(NoSystemFlowComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<NoSystemFlowComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): NoSystemFlowComponent
    }
}
