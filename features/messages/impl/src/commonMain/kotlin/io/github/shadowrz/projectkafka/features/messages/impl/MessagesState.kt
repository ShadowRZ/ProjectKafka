package io.github.shadowrz.projectkafka.features.messages.impl

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.mohamedrejeb.richeditor.model.RichTextState
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersState
import kotlinx.coroutines.flow.Flow

@Stable
data class MessagesState(
    val chat: AsyncOutcome<Chat>,
    val members: MembersState,
    val content: RichTextState,
    val messages: Flow<PagingData<ChatMessage>>,
    val sender: Sender,
    val eventSink: (MessagesEvents) -> Unit,
) : HanekokoroState
