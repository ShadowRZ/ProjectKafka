package io.github.shadowrz.projectkafka.features.home.impl.overview

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class OverviewComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: OverviewPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    interface Callback : Plugin {
        fun onAddMember()
    }

    private val callback = plugin<Callback>()

    internal val presenter = presenterFactory.create(callback)

    internal fun onAddMember() = callback.onAddMember()
}
