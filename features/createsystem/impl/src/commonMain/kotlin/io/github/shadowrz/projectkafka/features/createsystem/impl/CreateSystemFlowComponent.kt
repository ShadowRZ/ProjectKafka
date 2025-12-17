package io.github.shadowrz.projectkafka.features.createsystem.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemEntryPoint
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsComponent
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.serialization.Serializable
import timber.log.Timber

@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class CreateSystemFlowComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
) : Component(
        context = context,
        plugins = plugins,
    ),
    Resolver<CreateSystemFlowComponent.NavTarget, CreateSystemFlowComponent.Resolved> {
    private val logger = LoggerTag.Root

    private val navigation = StackNavigation<NavTarget>()

    val childStack: Value<ChildStack<*, Resolved>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.CreateSystem,
            handleBackButton = true,
            childFactory = ::resolve,
        )

    internal val callback = plugin<CreateSystemEntryPoint.Callback>()

    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved =
        when (navTarget) {
            is NavTarget.AddDetails -> {
                val callback =
                    object : AddDetailsComponent.Callback {
                        override fun onFinish(id: SystemID) {
                            Timber.tag(logger.value).d("Created system with ID $id")
                            callback.onFinished(id)
                        }
                    }

                val input =
                    AddDetailsComponent.Params(
                        systemName = navTarget.systemName,
                    )

                Resolved.AddDetails(
                    childComponent<AddDetailsComponent>(
                        context = componentContext,
                        plugins = listOf(callback, input),
                    ),
                )
            }

            NavTarget.CreateSystem -> {
                val callback =
                    object : CreateSystemComponent.Callback {
                        override fun onContinue(name: String) {
                            navigation.pushNew(NavTarget.AddDetails(name))
                        }
                    }
                Resolved.CreateSystem(
                    childComponent<CreateSystemComponent>(
                        context = componentContext,
                        plugins = listOf(callback),
                    ),
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
        data object CreateSystem : NavTarget

        @Serializable
        data class AddDetails(
            val systemName: String,
        ) : NavTarget
    }

    sealed interface Resolved {
        data class CreateSystem(
            val component: CreateSystemComponent,
        ) : Resolved

        data class AddDetails(
            val component: AddDetailsComponent,
        ) : Resolved
    }
}
