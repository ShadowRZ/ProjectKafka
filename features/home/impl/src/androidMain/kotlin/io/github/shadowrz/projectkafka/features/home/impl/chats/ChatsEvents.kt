package io.github.shadowrz.projectkafka.features.home.impl.chats

actual sealed interface ChatsEvents {
    actual data class ChangeChatsType(
        val chatsType: ChatsType?,
    ) : ChatsEvents
}
