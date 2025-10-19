package io.github.shadowrz.projectkafka.features.home.impl.chats

expect sealed interface ChatsEvents {
    class ChangeChatsType : ChatsEvents
}
