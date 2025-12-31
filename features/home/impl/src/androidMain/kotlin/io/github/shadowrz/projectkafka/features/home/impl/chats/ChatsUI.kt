package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.window.core.layout.WindowSizeClass
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.features.home.impl.HomeComponent
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.R
import io.github.shadowrz.projectkafka.features.home.impl.components.MenuAvatarButton
import io.github.shadowrz.projectkafka.libraries.components.SharedElements
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ChatBubbleOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.Check
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_system_subtitle
import org.jetbrains.compose.resources.stringResource

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalDecomposeApi::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
internal fun ChatsUI(
    state: ChatsState,
    system: System,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier =
            modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ChatsTopAppBar(
                system = system,
                scrollBehavior = scrollBehavior,
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

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
internal fun ChatsTopAppBar(
    system: System,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val useNavigationRail = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    MediumFlexibleTopAppBar(
        modifier = modifier,
        colors =
            topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        title = {
            Text(
                stringResource(R.string.home_nav_chat),
                fontWeight = FontWeight.Bold,
            )
        },
        subtitle = {
            Text(
                stringResource(
                    CommonStrings.common_system_subtitle,
                    system.name,
                    "",
                ).trim(),
                fontWeight = FontWeight.Light,
            )
        },
        actions = {
            if (!useNavigationRail) {
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
                    tooltip = { PlainTooltip { Text(stringResource(R.string.menu_tooltip)) } },
                    state = rememberTooltipState(),
                ) {
                    MenuAvatarButton(
                        avatar = system.avatar,
                        onClick = {
                            onAvatarClick()
                        },
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
                CircularWavyProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
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
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
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
                label = {
                    Text(
                        stringResource(it.desc),
                    )
                },
                leadingIcon = {
                    if (state.chatsType == it) {
                        Icon(
                            MaterialIcons.Check,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    } else {
                        Icon(
                            it.imageVector,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun Placeholder(modifier: Modifier = Modifier) {
    Text(
        stringResource(R.string.chats_empty_list),
        modifier = modifier.fillMaxSize().wrapContentSize().padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                    MaterialIcons.ChatBubbleOutline,
                    contentDescription = null,
                )
            },
            text = {
                Text(
                    stringResource(R.string.chats_new_chat),
                    modifier = Modifier.skipToLookaheadSize(),
                )
            },
        )
    }
}
