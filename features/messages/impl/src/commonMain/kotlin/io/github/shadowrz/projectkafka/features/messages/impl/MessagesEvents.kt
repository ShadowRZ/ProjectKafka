package io.github.shadowrz.projectkafka.features.messages.impl

import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroEvent

sealed interface MessagesEvents : HanekokoroEvent {

    data class ChangeSender(val sender: Sender) : MessagesEvents

    data object Send : MessagesEvents
}
