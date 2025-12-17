package io.github.shadowrz.projectkafka.features.licenses.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin

@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class LicensesComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    internal val presenter: LicensesPresenter,
) : Component(
        context = context,
        plugins = plugins,
    )
