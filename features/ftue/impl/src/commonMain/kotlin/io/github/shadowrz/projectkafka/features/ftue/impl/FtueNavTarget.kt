package io.github.shadowrz.projectkafka.features.ftue.impl

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface FtueNavTarget : NavKey {
    @Serializable data object Root : FtueNavTarget

    @Serializable data object Notifications : FtueNavTarget

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
