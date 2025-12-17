package io.github.shadowrz.projectkafka.features.home.impl.timeline

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class TimelineComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<TimelineState>,
) : Component(
        context = context,
        plugins = plugins,
    )
