package io.github.shadowrz.projectkafka.features.preferences.impl.root

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
import io.github.shadowrz.projectkafka.features.preferences.api.PreferencesEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner

@AssistedInject
@ContributesComponent(AppScope::class)
class PreferencesRootComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<PreferencesRootState>,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ),
    OnBackCallbackOwner {
    private val callback = plugin<PreferencesEntryPoint.Callback>()

    internal fun onDataManage() {
        callback.onDataManage()
    }
}
