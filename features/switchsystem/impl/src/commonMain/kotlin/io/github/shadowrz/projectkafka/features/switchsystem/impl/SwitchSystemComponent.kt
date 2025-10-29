package io.github.shadowrz.projectkafka.features.switchsystem.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

@AssistedInject
class SwitchSystemComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: SwitchSystemPresenter.Factory,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    OnBackCallbackOwner {
    private val callback = plugin<SwitchSystemEntryPoint.Callback>()

    internal val presenter = presenterFactory.create(callback)

    @ContributesIntoMap(
        AppScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(SwitchSystemComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<SwitchSystemComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): SwitchSystemComponent
    }
}
