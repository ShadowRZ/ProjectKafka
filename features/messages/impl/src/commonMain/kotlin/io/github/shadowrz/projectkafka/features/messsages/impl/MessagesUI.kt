package io.github.shadowrz.projectkafka.features.messsages.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.IconButton
import io.github.shadowrz.projectkafka.designsystem.IconButtonVariant
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.adaptive.HiddenInTwoPane
import io.github.shadowrz.projectkafka.designsystem.icons.Add
import io.github.shadowrz.projectkafka.designsystem.icons.SendOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.features.messsages.impl.components.MessageItem
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.kafkaui.ChatName

@Composable
@HanekokoroInject.ContributesRenderer(SystemScope::class)
internal fun MessagesUI(
    component: MessagesComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    MessagesUI(
        modifier = modifier,
        state = state,
        onBack = component::navigateUp,
    )
}

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
                Result.Loading -> {
                    LoadingTopAppBar(onBack = onBack)
                }

                is Result.Success<Chat> -> {
                    LoadedTopAppBar(
                        chat = state.chat.value,
                        onBack = onBack,
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Content(state = state, modifier = Modifier.weight(1f))
            Composer()
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
            HiddenInTwoPane {
                BackButton(onClick = onBack)
            }
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
                    color = KafkaTheme.materialColors.primary,
                    style = KafkaTheme.typography.titleMedium,
                )
            }
        },
        navigationIcon = {
            HiddenInTwoPane {
                BackButton(onClick = onBack)
            }
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
        verticalArrangement = Arrangement.Bottom,
    ) {
        items(
            items.itemCount,
            key = items.itemKey { it.id.value },
        ) { index ->
            items[index]?.let {
                MessageItem(it)
            }
        }
    }
}

@Composable
private fun Composer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        IconButton(
            onClick = {},
            variant = IconButtonVariant.FilledTonal,
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                KafkaIcons.Add,
                contentDescription = null,
            )
        }

        // TODO
        var content by
            rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue())
            }

        BasicTextField(
            modifier = Modifier.weight(1f).defaultMinSize(minHeight = 48.dp),
            value = content,
            onValueChange = { content = it },
            textStyle = TextStyle.Default.copy(color = KafkaTheme.materialColors.onSurface),
            decorationBox = { innerTextField ->
                Box(
                    modifier =
                        Modifier.clip(RoundedCornerShape(24.dp))
                            .border(width = 1.dp, color = KafkaTheme.materialColors.inverseOnSurface, shape = RoundedCornerShape(24.dp))
                            .background(KafkaTheme.materialColors.surfaceContainer)
                            .padding(12.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    innerTextField()
                }
            },
        )

        IconButton(
            onClick = {},
            variant = IconButtonVariant.Filled,
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                KafkaIcons.SendOutline,
                contentDescription = null,
            )
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewMessagesUI(@PreviewParameter(MessagesStateProvider::class) state: MessagesState) = KafkaPreview {
    MessagesUI(state = state)
}
