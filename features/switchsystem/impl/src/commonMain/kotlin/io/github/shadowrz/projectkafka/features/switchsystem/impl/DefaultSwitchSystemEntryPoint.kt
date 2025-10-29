package io.github.shadowrz.projectkafka.features.switchsystem.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.createComponent

@Inject
@ContributesBinding(AppScope::class)
class DefaultSwitchSystemEntryPoint : SwitchSystemEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        callback: SwitchSystemEntryPoint.Callback,
    ): Component =
        parent.createComponent<SwitchSystemComponent>(
            context = context,
            plugins = listOf(callback),
        )
}
