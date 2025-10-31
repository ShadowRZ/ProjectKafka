package io.github.shadowrz.projectkafka.features.home.impl.polls

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class PollsComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    )
