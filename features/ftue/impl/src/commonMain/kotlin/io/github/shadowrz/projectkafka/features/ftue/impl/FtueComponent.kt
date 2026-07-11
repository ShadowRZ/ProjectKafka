package io.github.shadowrz.projectkafka.features.ftue.impl

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationPresenter
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Stable
@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class FtueComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    internal val ftueService: DefaultFtueService,
    internal val notificationPresenterFactory: NotificationPresenter.Factory,
) :
    Component(
        context = context,
        plugins = plugins,
    ) {

    @Serializable
    sealed interface NavTarget : NavKey {
        @Serializable data object Root : NavTarget

        @Serializable data object Notifications : NavTarget

        companion object {
            internal val CONFIG = SavedStateConfiguration {
                serializersModule = SerializersModule {
                    polymorphic(NavKey::class) {
                        subclass(Root::class, Root.serializer())
                        subclass(Notifications::class, Notifications.serializer())
                    }
                }
            }
        }
    }
}
