package io.github.shadowrz.projectkafka.features.messages.impl

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import com.mohamedrejeb.richeditor.model.RichTextState
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.architecture.PageableItems
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersState

@Stable
data class MessagesState(
    val chat: AsyncOutcome<Chat>,
    val members: MembersState,
    val content: RichTextState,
    val messages: PageableItems<ChatMessage>,
    val sender: Sender,
    val lazyListState: LazyListState,
    val eventSink: (MessagesEvents) -> Unit,
) : HanekokoroState
