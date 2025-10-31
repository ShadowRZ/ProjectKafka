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
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemEntryPoint
import io.github.shadowrz.projectkafka.features.datamanage.api.DataManageEntryPoint
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugins
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.serialization.Serializable

@AssistedInject
@ContributesComponent(AppScope::class)
class NoSystemFlowComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    private val welcomeEntryPoint: WelcomeEntryPoint,
    private val createSystemEntryPoint: CreateSystemEntryPoint,
    private val dataManageEntryPoint: DataManageEntryPoint,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    Resolver<NoSystemFlowComponent.NavTarget, NoSystemFlowComponent.Resolved>,
    OnBackCallbackOwner {
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

                            override fun onDataManage() {
                                navigation.pushNew(NavTarget.DataManage)
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

            NavTarget.DataManage ->
                Resolved.DataManage(
                    dataManageEntryPoint.build(
                        this,
                        componentContext,
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

        @Serializable
        data object DataManage : NavTarget
    }

    sealed interface Resolved {
        data class Welcome(
            val component: Component,
        ) : Resolved

        data class CreateSystem(
            val component: Component,
        ) : Resolved

        data class DataManage(
            val component: Component,
        ) : Resolved
    }
}
