package io.github.shadowrz.projectkafka.features.about.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.about.api.AboutEntryPoint

@Inject
@ContributesBinding(AppScope::class)
class DefaultAboutEntryPoint : AboutEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        callback: AboutEntryPoint.Callback,
    ): Component =
        parent.childComponent<AboutComponent>(
            context = context,
            plugins = listOf(callback),
        )
}
