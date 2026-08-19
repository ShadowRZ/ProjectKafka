package io.github.shadowrz.projectkafka.features.messages.impl

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.CircularProgressIndicator
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.IconButton
import io.github.shadowrz.projectkafka.designsystem.IconButtonVariant
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaShapes
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.ModalBottomSheet
import io.github.shadowrz.projectkafka.designsystem.RadioButton
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.icons.FaceOutline
import io.github.shadowrz.projectkafka.designsystem.icons.SendOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.designsystem.preview.PreviewKafka
import io.github.shadowrz.projectkafka.features.messages.impl.components.MessageItem
import io.github.shadowrz.projectkafka.features.messages.impl.components.NarratorItem
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.core.map
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.kafkaui.ChatName
import io.github.shadowrz.projectkafka.libraries.kafkaui.MemberListItem
import io.github.shadowrz.projectkafka.libraries.richeditor.BasicRichTextEditor

@Composable
internal fun MessagesUI(
    state: MessagesState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            when (state.chat) {
                AsyncOutcome.Loading -> {
                    LoadingTopAppBar(onBack = onBack)
                }

                is AsyncOutcome.Success<Chat> -> {
                    LoadedTopAppBar(
                        chat = state.chat.value,
                        onBack = onBack,
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).consumeWindowInsets(innerPadding).imePadding()) {
            when (state.chat) {
                AsyncOutcome.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
                is AsyncOutcome.Success<*> -> {
                    Content(state = state, modifier = Modifier.weight(1f))
                    Composer(state = state)
                }
            }
        }
    }
}

@Composable
private fun LoadingTopAppBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            BackButton(onClick = onBack)
        },
    )
}

@Composable
private fun LoadedTopAppBar(
    chat: Chat,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar(
                    modifier = Modifier.size(36.dp),
                    avatar = chat.avatar?.value,
                )
                ChatName(
                    chat = chat,
                    color = KafkaTheme.colors.primary,
                    style = KafkaTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        },
    )
}

@Composable
private fun Content(
    state: MessagesState,
    modifier: Modifier = Modifier,
) {
    val items = state.messages.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.Bottom),
    ) {
        items(
            items.itemCount,
            key = items.itemKey { it.id.value },
        ) { index ->
            items[index]?.let {
                if (it.narrator) {
                    NarratorItem(it)
                } else {
                    MessageItem(
                        it,
                        showAvatar =
                            if (index == 0) true
                            else {
                                val prev = items.peek(index - 1)
                                if (prev?.narrator == true) true else prev?.member?.id != it.member.id
                            },
                        showName =
                            if (index == 0) true
                            else {
                                val prev = items.peek(index - 1)
                                if (prev?.narrator == true) true else prev?.member?.id != it.member.id
                            },
                        isMe = state.chat.map { chat -> chat.creatorID } == AsyncOutcome.Success(it.member.id),
                    )
                }
            }
        }
    }
}

@Composable
private fun Composer(
    state: MessagesState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        // IconButton(
        //     onClick = {},
        //     variant = IconButtonVariant.FilledTonal,
        //     modifier = Modifier.size(40.dp),
        // ) {
        //     Icon(
        //         KafkaIcons.Add,
        //         contentDescription = null,
        //     )
        // }

        val avatar =
            remember(state.sender) {
                when (state.sender) {
                    Sender.Narrator -> null
                    is Sender.Member ->
                        when (val members = state.members.members) {
                            AsyncOutcome.Loading -> null
                            is AsyncOutcome.Success<List<Member>> -> {
                                members.value.find { it.id == state.sender.memberID }?.avatar?.value
                            }
                        }
                }
            }

        var senderSheetOpen by rememberSaveable { mutableStateOf(false) }

        when (state.sender) {
            Sender.Narrator ->
                Icon(
                    modifier =
                        Modifier.size(40.dp)
                            .clip(CircleShape)
                            .clickable {
                                senderSheetOpen = true
                            }
                            .background(KafkaTheme.colors.primaryContainer)
                            .scale(0.75f),
                    imageVector = KafkaIcons.FaceOutline,
                    contentDescription = null,
                    tint = KafkaTheme.colors.onPrimaryContainer,
                )
            is Sender.Member ->
                Avatar(
                    avatar = avatar,
                    modifier =
                        Modifier.size(40.dp).clip(CircleShape).clickable {
                            senderSheetOpen = true
                        },
                )
        }

        if (senderSheetOpen) {
            ModalBottomSheet(onDismissRequest = { senderSheetOpen = false }) {
                when (val members = state.members.members) {
                    AsyncOutcome.Loading -> {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            LoadingIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                    is AsyncOutcome.Success<List<Member>> -> {
                        Text(
                            "Sender",
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 24.dp),
                            style = KafkaTheme.typography.titleLarge,
                        )
                        LazyColumn {
                            item {
                                val interactionSource = remember { MutableInteractionSource() }
                                val selected = state.sender == Sender.Narrator

                                fun onClick() {
                                    state.eventSink(MessagesEvents.ChangeSender(Sender.Narrator))
                                }

                                ListItem(
                                    modifier =
                                        Modifier.selectable(
                                            selected = selected,
                                            enabled = true,
                                            interactionSource = interactionSource,
                                            indication = LocalIndication.current,
                                            onClick = ::onClick,
                                        ),
                                    leadingContent = {
                                        Icon(
                                            modifier =
                                                Modifier.size(40.dp)
                                                    .clip(CircleShape)
                                                    .background(KafkaTheme.colors.primaryContainer)
                                                    .scale(0.75f),
                                            imageVector = KafkaIcons.FaceOutline,
                                            contentDescription = null,
                                            tint = KafkaTheme.colors.onPrimaryContainer,
                                        )
                                    },
                                    headlineContent = {
                                        Text("Narrator")
                                    },
                                    trailingContent = {
                                        RadioButton(
                                            selected = selected,
                                            onClick = ::onClick,
                                            interactionSource = interactionSource,
                                        )
                                    },
                                )
                            }
                            items(
                                items = members.value,
                                key = { it.id.value },
                            ) { member ->
                                val interactionSource = remember { MutableInteractionSource() }
                                val selected = state.sender == Sender.Member(member.id)

                                fun onClick() {
                                    state.eventSink(MessagesEvents.ChangeSender(Sender.Member(member.id)))
                                }

                                MemberListItem(
                                    member = member,
                                    modifier =
                                        Modifier.selectable(
                                            selected = selected,
                                            enabled = true,
                                            interactionSource = interactionSource,
                                            indication = LocalIndication.current,
                                            onClick = ::onClick,
                                        ),
                                ) {
                                    RadioButton(
                                        selected = selected,
                                        onClick = ::onClick,
                                        interactionSource = interactionSource,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        BasicRichTextEditor(
            modifier = Modifier.weight(1f).defaultMinSize(minHeight = 40.dp),
            state = state.content,
            textStyle = KafkaTheme.typography.bodyLarge.copy(color = KafkaTheme.colors.onSurface),
            cursorBrush = SolidColor(KafkaTheme.colors.onSurface),
            decorationBox = { innerTextField ->
                Box(
                    modifier =
                        Modifier.clip(KafkaShapes.Large)
                            .border(width = 1.dp, color = KafkaTheme.colors.surfaceVariant, shape = KafkaShapes.Large)
                            .background(KafkaTheme.colors.surfaceContainer)
                            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    innerTextField()
                }
            },
        )

        IconButton(
            onClick = { state.eventSink(MessagesEvents.Send) },
            variant = IconButtonVariant.Filled,
            modifier = Modifier.size(40.dp),
        ) {
            Icon(
                KafkaIcons.SendOutline,
                contentDescription = null,
                modifier = Modifier.scale(0.75f),
            )
        }
    }
}

@PreviewKafka
@Composable
internal fun PreviewMessagesUI(@PreviewParameter(MessagesStateProvider::class) state: MessagesState) = KafkaPreview {
    MessagesUI(state = state)
}
