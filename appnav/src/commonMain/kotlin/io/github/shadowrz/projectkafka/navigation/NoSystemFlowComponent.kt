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
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemEntryPoint
import io.github.shadowrz.projectkafka.features.datamanage.api.DataManageEntryPoint
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.serialization.Serializable

@AssistedInject
@HanekokoroInject(AppScope::class)
class NoSystemFlowComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    private val welcomeEntryPoint: WelcomeEntryPoint,
    private val createSystemEntryPoint: CreateSystemEntryPoint,
    private val dataManageEntryPoint: DataManageEntryPoint,
) : Component(
        context = context,
        plugins = plugins,
    ),
    Resolver<NoSystemFlowComponent.NavTarget, Component> {
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

    val childStack: Value<ChildStack<*, Component>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.Welcome,
            handleBackButton = true,
            childFactory = ::resolve,
        )

    private val callback = plugin<Callback>()

    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Component =
        when (navTarget) {
            NavTarget.Welcome -> {
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
                )
            }

            NavTarget.CreateSystem -> {
                createSystemEntryPoint.build(
                    this,
                    componentContext,
                    object : CreateSystemEntryPoint.Callback {
                        override fun onFinished(id: SystemID) {
                            callback.onFirstSystemCreated(id)
                        }
                    },
                )
            }

            NavTarget.DataManage -> {
                dataManageEntryPoint.build(
                    this,
                    componentContext,
                )
            }
        }

    internal fun onBack() {
        onNavigateUp { }
    }

    override fun onNavigateUp(onComplete: (Boolean) -> Unit) {
        navigation.pop(onComplete)
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
}
