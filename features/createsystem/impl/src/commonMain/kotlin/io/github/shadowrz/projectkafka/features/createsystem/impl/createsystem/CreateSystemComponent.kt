package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.architecture.plugins

@AssistedInject
class CreateSystemComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<CreateSystemState>,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ) {
    interface Callback : Plugin {
        fun onContinue(name: String)
    }

    internal val callback = plugins<Callback>().first()

    @ContributesIntoMap(
        AppScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(CreateSystemComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<CreateSystemComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): CreateSystemComponent
    }
}
