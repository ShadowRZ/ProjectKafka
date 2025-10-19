package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import kotlinx.coroutines.flow.Flow

@Stable
actual data class ChatsState(
    val chats: Flow<PagingData<Chat>>,
    val chatsType: ChatsType?,
    val eventSink: (ChatsEvents) -> Unit,
)
