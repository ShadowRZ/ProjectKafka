package io.github.shadowrz.projectkafka.features.ftue.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class FtueComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    private val ftueService: DefaultFtueService,
    coroutineDispatchers: CoroutineDispatchers,
) : Component(
        context = context,
        plugins = plugins,
    ),
    Resolver<FtueComponent.NavTarget, FtueComponent.Resolved> {
    init {
        lifecycle.doOnCreate {
            moveToNextStepIfNeeded()
        }
    }

    private val lifecycleScope = coroutineScope(coroutineDispatchers.main)

    private val navigation = SlotNavigation<NavTarget>()

    internal val step: Value<ChildSlot<*, Resolved>> =
        childSlot(
            source = navigation,
            serializer = null,
            initialConfiguration = { NavTarget.Root },
            handleBackButton = true,
            childFactory = ::resolve,
        )

    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved =
        when (navTarget) {
            NavTarget.Root -> {
                Resolved.Root
            }

            NavTarget.Notifications -> {
                val callback =
                    object : NotificationComponent.Callback {
                        override fun onDone() {
                            moveToNextStepIfNeeded()
                        }
                    }

                Resolved.Notifications(
                    childComponent<NotificationComponent>(
                        context = componentContext,
                        plugins = listOf(callback),
                    ),
                )
            }
        }

    private fun moveToNextStepIfNeeded() =
        lifecycleScope.launch {
            when (ftueService.nextStep()) {
                FtueStep.NotificationOptIn -> navigation.activate(NavTarget.Notifications)
                null -> ftueService.updateState()
            }
        }

    @Serializable
    sealed interface NavTarget {
        @Serializable
        data object Root : NavTarget

        @Serializable
        data object Notifications : NavTarget
    }

    sealed interface Resolved {
        data object Root : Resolved

        data class Notifications(
            val component: NotificationComponent,
        ) : Resolved
    }
}
