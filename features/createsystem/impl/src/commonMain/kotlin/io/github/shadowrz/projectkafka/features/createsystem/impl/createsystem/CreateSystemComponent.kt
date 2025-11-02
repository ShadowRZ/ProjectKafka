package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.Presenter
import io.github.shadowrz.hanekokoro.framework.runtime.plugins

@AssistedInject
@ContributesComponent(AppScope::class)
class CreateSystemComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<CreateSystemState>,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ) {
    interface Callback : Plugin {
        fun onContinue(name: String)
    }

    internal val callback = plugins<Callback>().first()
}
