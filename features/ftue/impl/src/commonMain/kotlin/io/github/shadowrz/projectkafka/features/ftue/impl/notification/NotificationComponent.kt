package io.github.shadowrz.projectkafka.features.ftue.impl.notification

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
class NotificationComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    notificationPresenterFactory: NotificationPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    interface Callback : Plugin {
        fun onDone()
    }

    internal val callback: Callback = plugin<Callback>()

    internal val presenter = notificationPresenterFactory.create(callback)
}
