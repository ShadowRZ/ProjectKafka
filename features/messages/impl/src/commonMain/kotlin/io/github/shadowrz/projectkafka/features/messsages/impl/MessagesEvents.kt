package io.github.shadowrz.projectkafka.features.messsages.impl

import androidx.compose.ui.text.input.TextFieldValue
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroEvent

sealed interface MessagesEvents : HanekokoroEvent {
    data class UpdateContent(val content: TextFieldValue) : MessagesEvents

    data class ChangeSender(val sender: Sender) : MessagesEvents

    data object Send : MessagesEvents
}
