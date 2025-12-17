package io.github.shadowrz.projectkafka.features.home.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.home.api.HomeEntryPoint
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesBinding(SystemScope::class)
class DefaultHomeEntryPoint : HomeEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        callback: HomeEntryPoint.Callback,
    ): Component =
        parent.childComponent<HomeComponent>(
            context = context,
            plugins = listOf(callback),
        )
}
