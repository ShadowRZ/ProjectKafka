package io.github.shadowrz.projectkafka.features.home.impl.overview

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class OverviewComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
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
}
