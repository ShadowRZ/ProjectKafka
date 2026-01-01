package io.github.shadowrz.projectkafka.features.home.impl.chats

sealed interface ChatsEvents {
    data class ChangeChatsType(
        val chatsType: ChatsType?,
    ) : ChatsEvents
}
