package io.github.shadowrz.projectkafka.features.ftue.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.projectkafka.features.ftue.api.FtueEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.createComponent
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesBinding(SystemScope::class)
class DefaultFtueEntryPoint : FtueEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
    ): Component =
        parent.createComponent<FtueComponent>(
            context = context,
        )
}
