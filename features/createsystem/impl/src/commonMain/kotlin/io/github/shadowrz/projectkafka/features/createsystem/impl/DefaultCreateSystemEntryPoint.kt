package io.github.shadowrz.projectkafka.features.createsystem.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemEntryPoint

@Inject
@ContributesBinding(AppScope::class)
class DefaultCreateSystemEntryPoint : CreateSystemEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        callback: CreateSystemEntryPoint.Callback,
    ): Component =
        parent.childComponent<CreateSystemFlowComponent>(
            context = context,
            plugins = listOf(callback),
        )
}
