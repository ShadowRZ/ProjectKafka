package io.github.shadowrz.projectkafka.features.messsages.impl

import androidx.savedstate.serialization.SavedStateConfiguration
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

sealed interface Sender {
    @Serializable data object Narrator : Sender

    @Serializable data class Member(val memberID: MemberID) : Sender

    companion object {
        val CONFIG = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(Sender::class) {
                    subclass(Narrator::class, Narrator.serializer())
                    subclass(Member::class, Member.serializer())
                }
            }
        }
    }
}
