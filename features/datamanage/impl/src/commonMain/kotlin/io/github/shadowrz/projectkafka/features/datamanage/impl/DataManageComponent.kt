package io.github.shadowrz.projectkafka.features.datamanage.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter

@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class DataManageComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<DataManageState>,
) : Component(
        context = context,
        plugins = plugins,
    )
