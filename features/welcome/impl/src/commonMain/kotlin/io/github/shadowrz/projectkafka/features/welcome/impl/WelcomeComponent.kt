package io.github.shadowrz.projectkafka.features.welcome.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.architecture.plugin

@AssistedInject
@ContributesComponent(AppScope::class)
class WelcomeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<WelcomeState>,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ) {
    internal val callback = plugin<WelcomeEntryPoint.Callback>()
}
