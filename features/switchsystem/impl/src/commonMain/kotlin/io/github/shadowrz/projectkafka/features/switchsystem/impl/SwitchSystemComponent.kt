package io.github.shadowrz.projectkafka.features.switchsystem.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner

@AssistedInject
@ContributesComponent(AppScope::class)
class SwitchSystemComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: SwitchSystemPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ),
    OnBackCallbackOwner {
    private val callback = plugin<SwitchSystemEntryPoint.Callback>()

    internal val presenter = presenterFactory.create(callback)
}
