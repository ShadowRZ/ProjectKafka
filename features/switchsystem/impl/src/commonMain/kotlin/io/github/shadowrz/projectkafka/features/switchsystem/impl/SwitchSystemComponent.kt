package io.github.shadowrz.projectkafka.features.switchsystem.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemEntryPoint

@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class SwitchSystemComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: SwitchSystemPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    private val callback = plugin<SwitchSystemEntryPoint.Callback>()

    internal val presenter = presenterFactory.create(callback)
}
