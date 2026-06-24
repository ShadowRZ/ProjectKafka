package io.github.shadowrz.projectkafka.features.messsages.impl

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import kotlinx.coroutines.flow.Flow

@Stable
data class MessagesState(
    val chat: Result<Chat>,
    val messages: Flow<PagingData<ChatMessage>>,
) : HanekokoroState
