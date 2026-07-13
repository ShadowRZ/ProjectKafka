package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.runtime.Immutable
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Immutable
sealed interface HomeNavTarget {
    @Serializable data object Overview : HomeNavTarget

    @Serializable data object Timeline : HomeNavTarget

    @Serializable data object Chats : HomeNavTarget

    @Serializable data object Polls : HomeNavTarget

    companion object {
        internal val CONFIG = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(HomeNavTarget::class) {
                    subclass(Overview::class, Overview.serializer())
                    subclass(Timeline::class, Timeline.serializer())
                    subclass(Chats::class, Chats.serializer())
                    subclass(Polls::class, Polls.serializer())
                }
            }
        }
    }
}
