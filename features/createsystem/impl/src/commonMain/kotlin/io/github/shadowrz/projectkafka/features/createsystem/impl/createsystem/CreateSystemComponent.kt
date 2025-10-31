package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.architecture.plugins

@AssistedInject
@ContributesComponent(AppScope::class)
class CreateSystemComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
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
}
