package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.designsystem.FilterChip
import io.github.shadowrz.projectkafka.designsystem.FilterRow
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.designsystem.icons.ChatBubbleOutline
import io.github.shadowrz.projectkafka.features.home.impl.HomeComponent
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.SharedElements
import io.github.shadowrz.projectkafka.features.home.impl.components.BaseTopAppBar
import io.github.shadowrz.projectkafka.libraries.data.api.System
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.chats_empty_list
import projectkafka.features.home.impl.generated.resources.chats_new_chat
import projectkafka.features.home.impl.generated.resources.home_nav_chat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatsUI(
    state: ChatsState,
    system: System,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ChatsTopAppBar(
                system = system,
                onAvatarClick = onAvatarClick,
            )
        },
        bottomBar = {
            NavigationBar(navTarget = HomeComponent.MainNavTarget.Chats)
        },
        floatingActionButton = {
            ChatsFloatingActionButton()
        },
    ) { innerPadding ->
        ChatsContent(
            state = state,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
internal fun ChatsTopAppBar(
    system: System,
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
    )
}

@Composable
internal fun ChatsContent(
    state: ChatsState,
    modifier: Modifier = Modifier,
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
                        key = chats.itemKey { it.id },
                    ) { index ->
                        val chat = chats[index]
                        if (chat != null) {
                            Text(chat.name ?: "<Unknown>")
                        }
                    }
                }
            }
        }
    }
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
        color = KafkaTheme.materialColors.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ChatsFloatingActionButton(
    modifier: Modifier = Modifier,
    onNewChat: () -> Unit = {},
) {
    SharedElementTransitionScope {
        ExtendedFloatingActionButton(
            onClick = onNewChat,
            modifier =
                modifier.sharedElement(
                    sharedContentState =
                        rememberSharedContentState(
                            SharedElements.FloatingActionButton,
                        ),
                    animatedVisibilityScope =
                        requireAnimatedScope(
                            SharedElementTransitionScope.AnimatedScope.Navigation,
                        ),
                ),
            icon = {
                Icon(
                    KafkaIcons.ChatBubbleOutline,
                    contentDescription = null,
                )
            },
            text = {
                Text(
                    stringResource(Res.string.chats_new_chat),
                    modifier = Modifier.skipToLookaheadSize(),
                )
            },
        )
    }
}
