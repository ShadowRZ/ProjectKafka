package io.github.shadowrz.projectkafka.features.home.impl.timeline

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class TimelineComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<TimelineState>,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    )
