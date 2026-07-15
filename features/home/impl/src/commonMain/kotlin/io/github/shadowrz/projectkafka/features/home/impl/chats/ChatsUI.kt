package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.FilterChip
import io.github.shadowrz.projectkafka.designsystem.FilterRow
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.features.home.impl.HomeNavTarget
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.components.BaseTopAppBar
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.kafkaui.ChatName
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.chats_empty_list
import projectkafka.features.home.impl.generated.resources.home_nav_chat

@Composable
internal fun ChatsUI(
    state: ChatsState,
    system: System,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    SharedTransitionScope {
        Scaffold(
            modifier = modifier.then(it),
            topBar = {
                AnimatedVisibility(visible = true) {
                    ChatsTopAppBar(
                        system = system,
                        onAvatarClick = onAvatarClick,
                        sharedTransitionScope = this@SharedTransitionScope,
                        animatedVisibilityScope = this,
                    )
                }
            },
            bottomBar = {
                NavigationBar(navTarget = HomeNavTarget.Chats)
            },
        ) { innerPadding ->
            ChatsContent(
                state = state,
                modifier = Modifier.fillMaxSize().padding(innerPadding).imePadding(),
            )
        }
    }
}

@Composable
@NonRestartableComposable
internal fun ChatsTopAppBar(
    system: System,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onAvatarClick: () -> Unit = {},
) {
    BaseTopAppBar(
        modifier = modifier,
        system = system,
        title = stringResource(Res.string.home_nav_chat),
        scrollBehavior = scrollBehavior,
        onAvatarClick = onAvatarClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@Composable
internal fun ChatsContent(
    state: ChatsState,
    modifier: Modifier = Modifier,
    onOpenChat: (ChatID) -> Unit = {},
) {
    val lazyListState = rememberLazyListState()
    val chats = state.chats.collectAsLazyPagingItems()

    Column(modifier = modifier) {
        FilterChips(state = state)
        Crossfade(state.chats) { chats ->
            val chats = chats.collectAsLazyPagingItems()

            if (chats.loadState.refresh == LoadState.Loading) {
                LoadingIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
            } else if (chats.itemCount == 0) {
                Placeholder()
            } else {
                LazyColumn(state = lazyListState) {
                    items(
                        count = chats.itemCount,
                        key = chats.itemKey { it.id.value },
                    ) { index ->
                        chats[index]?.let {
                            ChatItem(
                                chat = it,
                                onOpenChat = { onOpenChat(it.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatItem(
    chat: Chat,
    modifier: Modifier = Modifier,
    onOpenChat: () -> Unit = {},
) {
    ListItem(
        modifier = modifier,
        onClick = onOpenChat,
        headlineContent = {
            ChatName(chat = chat)
        },
        supportingContent = {
            Text("<Unknown>")
        },
        leadingContent = {
            Avatar(
                avatar = chat.avatar?.value,
                modifier = Modifier.size(40.dp),
            )
        },
    )
}

@Composable
private fun FilterChips(
    state: ChatsState,
    modifier: Modifier = Modifier,
) {
    FilterRow(modifier = modifier) {
        ChatsType.entries.forEach {
            FilterChip(
                selected = state.chatsType == it,
                onClick = {
                    if (state.chatsType == it) {
                        state.eventSink(ChatsEvents.ChangeChatsType(null))
                    } else {
                        state.eventSink(ChatsEvents.ChangeChatsType(it))
                    }
                },
                label = stringResource(it.desc),
                leadingIcon = it.imageVector,
            )
        }
    }
}

@Composable
private fun Placeholder(modifier: Modifier = Modifier) {
    Text(
        stringResource(Res.string.chats_empty_list),
        modifier = modifier.fillMaxSize().wrapContentSize().padding(horizontal = 16.dp),
        color = KafkaTheme.colors.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )
}
