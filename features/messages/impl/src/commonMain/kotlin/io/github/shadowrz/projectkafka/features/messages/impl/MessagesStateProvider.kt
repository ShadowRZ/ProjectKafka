package io.github.shadowrz.projectkafka.features.messages.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.PagingData
import com.mohamedrejeb.richeditor.model.RichTextState
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MessageID
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersState
import kotlin.time.Instant
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate

class MessagesStateProvider : PreviewParameterProvider<MessagesState> {
    override val values: Sequence<MessagesState>
        get() = sequenceOf(aEmptyMessagesState(), aMessagesState())
}

private fun aEmptyMessagesState(): MessagesState =
    MessagesState(
        chat =
            AsyncOutcome.Success(
                Chat(
                    id = ChatID("1"),
                    name = null,
                    avatar = null,
                    creatorID = MemberID("1"),
                )
            ),
        messages = flowOf(PagingData.empty()),
        content = RichTextState(),
        members = MembersState(members = AsyncOutcome.Success(emptyList())),
        sender = Sender.Narrator,
    ) {}

private fun aMessagesState(): MessagesState =
    MessagesState(
        chat =
            AsyncOutcome.Success(
                Chat(
                    id = ChatID("1"),
                    name = null,
                    avatar = null,
                    creatorID = MemberID("1"),
                )
            ),
        content = RichTextState(),
        messages =
            flowOf(
                PagingData.from(
                    listOf(
                        ChatMessage(
                            id = MessageID(1),
                            member =
                                Member(
                                    id = MemberID("1"),
                                    name = "N",
                                    description = "",
                                    avatar = null,
                                    cover = null,
                                    preferences = "",
                                    roles = "",
                                    birth = LocalDate(2024, 1, 1),
                                    admin = false,
                                ),
                            content = "Hello World",
                            media = null,
                            timestamp = Instant.fromEpochSeconds(1710630000),
                        ),
                        ChatMessage(
                            id = MessageID(2),
                            member =
                                Member(
                                    id = MemberID("1"),
                                    name = "N",
                                    description = "",
                                    avatar = null,
                                    cover = null,
                                    preferences = "",
                                    roles = "",
                                    birth = LocalDate(2024, 1, 1),
                                    admin = false,
                                ),
                            content = "This is a test",
                            media = null,
                            timestamp = Instant.fromEpochSeconds(1710630000),
                        ),
                    )
                )
            ),
        members = MembersState(members = AsyncOutcome.Success(emptyList())),
        sender = Sender.Narrator,
    ) {}
