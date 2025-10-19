package io.github.shadowrz.projectkafka.features.welcome.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.createComponent

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultWelcomeEntryPoint : WelcomeEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        callback: WelcomeEntryPoint.Callback,
    ): Component =
        parent.createComponent<WelcomeComponent>(
            context = context,
            plugins = listOf(callback),
        )
}
