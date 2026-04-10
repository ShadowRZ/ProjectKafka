package io.github.shadowrz.projectkafka.features.quickstart.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.quickstart.api.QuickStartEntryPoint

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultQuickStartEntryPoint : QuickStartEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        callback: QuickStartEntryPoint.Callback,
    ): Component =
        parent.childComponent<QuickStartComponent>(
            context = context,
            plugins = listOf(callback),
        )
}
