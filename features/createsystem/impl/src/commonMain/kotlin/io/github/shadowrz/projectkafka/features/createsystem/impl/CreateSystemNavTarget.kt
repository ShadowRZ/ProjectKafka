package io.github.shadowrz.projectkafka.features.createsystem.impl

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface CreateSystemNavTarget : NavKey {
    @Serializable data object CreateSystem : CreateSystemNavTarget

    @Serializable data class AddDetails(val systemName: String) : CreateSystemNavTarget

    companion object {
        internal val CONFIG = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(CreateSystem::class, CreateSystem.serializer())
                    subclass(AddDetails::class, AddDetails.serializer())
                }
            }
        }
    }
}
