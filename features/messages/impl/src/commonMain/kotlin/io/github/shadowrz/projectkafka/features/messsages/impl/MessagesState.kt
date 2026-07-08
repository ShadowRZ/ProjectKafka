package io.github.shadowrz.projectkafka.features.messsages.impl

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersState
import kotlinx.coroutines.flow.Flow

@Stable
data class MessagesState(
    val chat: Result<Chat>,
    val members: MembersState,
    val content: TextFieldValue,
    val messages: Flow<PagingData<ChatMessage>>,
    val eventSink: (MessagesEvents) -> Unit,
) : HanekokoroState
