package io.github.shadowrz.projectkafka.features.about.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.features.about.api.AboutEntryPoint

@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class AboutComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    internal val presenter: AboutPresenter,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    internal val callback = plugin<AboutEntryPoint.Callback>()
}
