package io.github.shadowrz.projectkafka.features.messages.api

import androidx.navigation3.runtime.NavKey
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import kotlinx.serialization.Serializable

@Serializable data class MessagesScreen(val chatID: ChatID) : NavKey
