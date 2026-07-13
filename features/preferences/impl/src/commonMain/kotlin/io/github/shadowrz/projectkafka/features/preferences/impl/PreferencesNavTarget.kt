package io.github.shadowrz.projectkafka.features.preferences.impl

import androidx.compose.runtime.Immutable
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Immutable
@Serializable
sealed interface PreferencesNavTarget : NavKey {
    @Serializable data object Root : PreferencesNavTarget

    companion object {
        val CONFIG = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Root::class, Root.serializer())
                }
            }
        }
    }
}
