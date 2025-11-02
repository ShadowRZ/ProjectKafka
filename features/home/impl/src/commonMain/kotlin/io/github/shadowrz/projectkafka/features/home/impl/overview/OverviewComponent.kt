package io.github.shadowrz.projectkafka.features.home.impl.overview

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class OverviewComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: OverviewPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ) {
    interface Callback : Plugin {
        fun onAddMember()
    }

    private val callback = plugin<Callback>()

    internal val presenter = presenterFactory.create(callback)

    internal fun onAddMember() = callback.onAddMember()
}
