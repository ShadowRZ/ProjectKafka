package io.github.shadowrz.projectkafka.features.ftue.impl.notification

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugins
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class NotificationComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    notificationPresenterFactory: NotificationPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ) {
    interface Callback : Plugin {
        fun onDone()
    }

    internal val callback: Callback = plugins<Callback>().first()

    internal val presenter = notificationPresenterFactory.create(callback)
}
