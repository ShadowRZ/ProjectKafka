package io.github.shadowrz.projectkafka.features.welcome.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.Presenter
import io.github.shadowrz.hanekokoro.framework.runtime.plugin
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeEntryPoint

@AssistedInject
@ContributesComponent(AppScope::class)
class WelcomeComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<WelcomeState>,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ) {
    internal val callback = plugin<WelcomeEntryPoint.Callback>()
}
