package io.github.shadowrz.projectkafka.features.welcome.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.architecture.plugin

@AssistedInject
class WelcomeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<WelcomeState>,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ) {
    internal val callback = plugin<WelcomeEntryPoint.Callback>()

    @ContributesIntoMap(
        AppScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(WelcomeComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<WelcomeComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): WelcomeComponent
    }
}
