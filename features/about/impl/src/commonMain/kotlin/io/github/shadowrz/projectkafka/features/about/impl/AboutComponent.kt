package io.github.shadowrz.projectkafka.features.about.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.features.about.api.AboutEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugin

@AssistedInject
@ContributesComponent(AppScope::class)
class AboutComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    OnBackCallbackOwner {
    internal val callback = plugin<AboutEntryPoint.Callback>()
}
