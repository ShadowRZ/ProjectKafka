package io.github.shadowrz.projectkafka.features.ftue.impl.notification

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugins
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class NotificationComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    notificationPresenterFactory: NotificationPresenter.Factory,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ) {
    interface Callback : Plugin {
        fun onDone()
    }

    internal val callback: Callback = plugins<Callback>().first()

    internal val presenter = notificationPresenterFactory.create(callback)
}
