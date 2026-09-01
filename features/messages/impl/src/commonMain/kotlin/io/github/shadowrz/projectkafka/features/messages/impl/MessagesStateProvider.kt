package io.github.shadowrz.projectkafka.features.messages.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mohamedrejeb.richeditor.model.RichTextState
import io.github.shadowrz.projectkafka.libraries.architecture.PageableItems
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MessageID
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersState
import kotlin.time.Instant
import kotlinx.datetime.LocalDate

class MessagesStateProvider : PreviewParameterProvider<MessagesState> {
    override val values: Sequence<MessagesState>
        get() =
            sequenceOf(
                aLoadingMessagesState(),
                aEmptyMessagesState(),
                aMessagesState(),
                aMessagesStateWithNarrator(),
            )
}

internal fun aLoadingMessagesState(): MessagesState =
    MessagesState(
        chat = AsyncOutcome.Loading,
        messages = PageableItems.Preview(emptyList()),
        content = RichTextState(),
        members = MembersState(members = AsyncOutcome.Success(emptyList())),
        sender = Sender.Narrator,
    ) {}

internal fun aEmptyMessagesState(
    name: String? = null,
    eventSink: (MessagesEvents) -> Unit = {},
): MessagesState =
    MessagesState(
        chat =
            AsyncOutcome.Success(
                Chat(
                    id = ChatID("1"),
                    name = name,
                    avatar = null,
                    creatorID = MemberID("1"),
                )
            ),
        messages = PageableItems.Preview(emptyList()),
        content = RichTextState(),
        members = MembersState(members = AsyncOutcome.Success(emptyList())),
        sender = Sender.Narrator,
        eventSink = eventSink,
    )

internal fun aMessagesState(
    name: String? = null,
    members: List<Member> = emptyList(),
    sender: Sender = Sender.Narrator,
    eventSink: (MessagesEvents) -> Unit = {},
): MessagesState =
    MessagesState(
        chat =
            AsyncOutcome.Success(
                Chat(
                    id = ChatID("1"),
                    name = name,
                    avatar = null,
                    creatorID = MemberID("1"),
                )
            ),
        content = RichTextState(),
        messages =
            PageableItems.Preview(
                listOf(
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
                )
            ),
        members = MembersState(members = AsyncOutcome.Success(members)),
        sender = sender,
        eventSink = eventSink,
    )

internal fun aMessagesStateWithNarrator(
    name: String? = null,
    members: List<Member> = emptyList(),
    sender: Sender = Sender.Narrator,
    eventSink: (MessagesEvents) -> Unit = {},
): MessagesState =
    MessagesState(
        chat =
            AsyncOutcome.Success(
                Chat(
                    id = ChatID("1"),
                    name = name,
                    avatar = null,
                    creatorID = MemberID("1"),
                )
            ),
        content = RichTextState(),
        messages =
            PageableItems.Preview(
                listOf(
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
                    ChatMessage(
                        id = MessageID(1),
                        member =
                            Member(
                                id = MemberID("2"),
                                name = "N2",
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
                        narrator = true,
                    ),
                )
            ),
        members = MembersState(members = AsyncOutcome.Success(members)),
        sender = sender,
        eventSink = eventSink,
    )

internal fun aMessagesStateWithMembers(
    name: String? = null,
    members: List<Member> = emptyList(),
    sender: Sender = Sender.Narrator,
    eventSink: (MessagesEvents) -> Unit = {},
): MessagesState =
    MessagesState(
        chat =
            AsyncOutcome.Success(
                Chat(
                    id = ChatID("1"),
                    name = name,
                    avatar = null,
                    creatorID = MemberID("1"),
                )
            ),
        content = RichTextState(),
        messages = PageableItems.Preview(emptyList()),
        members = MembersState(members = AsyncOutcome.Success(members)),
        sender = sender,
        eventSink = eventSink,
    )

internal fun aMessagesStateWithLoadingMembers(
    name: String? = null,
    eventSink: (MessagesEvents) -> Unit = {},
): MessagesState =
    MessagesState(
        chat =
            AsyncOutcome.Success(
                Chat(
                    id = ChatID("1"),
                    name = name,
                    avatar = null,
                    creatorID = MemberID("1"),
                )
            ),
        content = RichTextState(),
        messages = PageableItems.Preview(emptyList()),
        members = MembersState(members = AsyncOutcome.Loading),
        sender = Sender.Narrator,
        eventSink = eventSink,
    )
