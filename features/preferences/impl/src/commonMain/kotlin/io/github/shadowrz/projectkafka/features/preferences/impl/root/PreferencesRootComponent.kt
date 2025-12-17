package io.github.shadowrz.projectkafka.features.preferences.impl.root

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.features.preferences.api.PreferencesEntryPoint

@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class PreferencesRootComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<PreferencesRootState>,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    private val callback = plugin<PreferencesEntryPoint.Callback>()

    internal fun onDataManage() {
        callback.onDataManage()
    }
}
