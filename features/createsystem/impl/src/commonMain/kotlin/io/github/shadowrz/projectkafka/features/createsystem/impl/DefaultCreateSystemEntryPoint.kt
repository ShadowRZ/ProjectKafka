package io.github.shadowrz.projectkafka.features.createsystem.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.createComponent

@Inject
@ContributesBinding(AppScope::class)
class DefaultCreateSystemEntryPoint : CreateSystemEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        callback: CreateSystemEntryPoint.Callback,
    ): Component =
        parent.createComponent<CreateSystemFlowComponent>(
            context = context,
            plugins = listOf(callback),
        )
}
