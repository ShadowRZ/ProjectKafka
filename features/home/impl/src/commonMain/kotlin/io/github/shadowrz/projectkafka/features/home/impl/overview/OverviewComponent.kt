package io.github.shadowrz.projectkafka.features.home.impl.overview

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
class OverviewComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: OverviewPresenter.Factory,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ) {
    interface Callback : Plugin {
        fun onAddMember()
    }

    private val callback = plugin<Callback>()

    internal val presenter = presenterFactory.create(callback)

    internal fun onAddMember() = callback.onAddMember()

    @ContributesIntoMap(
        SystemScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(OverviewComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<OverviewComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): OverviewComponent
    }
}
