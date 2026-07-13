package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.slack.circuit.sharedelements.ProvideAnimatedTransitionScope
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.designsystem.FloatingActionButtonMenu
import io.github.shadowrz.projectkafka.designsystem.FloatingActionButtonMenuItem
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.NavigationBar
import io.github.shadowrz.projectkafka.designsystem.NavigationBarItem
import io.github.shadowrz.projectkafka.designsystem.NavigationRail
import io.github.shadowrz.projectkafka.designsystem.NavigationRailItem
import io.github.shadowrz.projectkafka.designsystem.RadioButton
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.SingleSelectionDialog
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.ToggleFloatingActionButton
import io.github.shadowrz.projectkafka.designsystem.ToggleFloatingActionButtonDefaults.animateIcon
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.designsystem.adaptive.AdaptiveLayout
import io.github.shadowrz.projectkafka.designsystem.adaptive.adaptiveValue
import io.github.shadowrz.projectkafka.designsystem.icons.Add
import io.github.shadowrz.projectkafka.designsystem.icons.ChatBubbleOutline
import io.github.shadowrz.projectkafka.designsystem.icons.Close
import io.github.shadowrz.projectkafka.designsystem.icons.DashboardOutline
import io.github.shadowrz.projectkafka.designsystem.icons.PersonOutline
import io.github.shadowrz.projectkafka.designsystem.icons.Poll
import io.github.shadowrz.projectkafka.designsystem.icons.Timeline
import io.github.shadowrz.projectkafka.designsystem.pinnedExitUntilCollapsedScrollBehavior
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.components.MenuAvatarButton
import io.github.shadowrz.projectkafka.features.home.impl.components.SystemDialog
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.polls.PollsTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelineTopAppBar
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.kafkaui.KafkaHelpSheet
import io.github.shadowrz.projectkafka.libraries.kafkaui.MemberListItem
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_new_chat
import io.github.shadowrz.projectkafka.libraries.strings.common_new_member
import io.github.shadowrz.projectkafka.libraries.strings.common_new_poll
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.chats_new_chat_dialog_subtitle
import projectkafka.features.home.impl.generated.resources.chats_new_chat_dialog_title
import projectkafka.features.home.impl.generated.resources.home_nav_chat
import projectkafka.features.home.impl.generated.resources.home_nav_overview
import projectkafka.features.home.impl.generated.resources.home_nav_poll
import projectkafka.features.home.impl.generated.resources.home_nav_timeline

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeUI(
    state: HomeState,
    navTarget: HomeNavTarget,
    lookaheadScope: LookaheadScope,
    modifier: Modifier = Modifier,
    onNewNavTarget: (HomeNavTarget) -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
) {
    val useNavigationRail = AdaptiveLayout.useNavigationRail()

    val excludedNavigationInset = if (!useNavigationRail) WindowInsets.navigationBars else WindowInsets()

    NavigationRailScaffold(
        navigationRail = {
            AnimatedVisibility(
                visible = useNavigationRail,
                enter = slideInHorizontally(initialOffsetX = { -it }),
                exit = slideOutHorizontally(targetOffsetX = { -it }),
            ) {
                NavigationRail(
                    avatar = state.system.avatar?.value,
                    navTarget = navTarget,
                    onNewNavTarget = onNewNavTarget,
                    onAvatarClick = {
                        state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.SystemMenu))
                    },
                )
            }
        }
    ) {
        val scrollBehavior = pinnedExitUntilCollapsedScrollBehavior()

        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection).animateBounds(lookaheadScope = lookaheadScope),
            topBar = {
                TopAppBar(
                    system = state.system,
                    navTarget = navTarget,
                    scrollBehavior = scrollBehavior,
                    onAvatarClick = {
                        state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.SystemMenu))
                    },
                )
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = !useNavigationRail,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
                    NavigationBar(
                        navTarget = navTarget,
                        onNewNavTarget = onNewNavTarget,
                    )
                }
            },
            floatingActionButton = floatingActionButton,
            contentWindowInsets =
                WindowInsets.systemBars
                    .exclude(excludedNavigationInset.only(WindowInsetsSides.Vertical))
                    .exclude(WindowInsets.displayCutout),
        ) { innerPadding ->
            content(innerPadding)
        }
    }

    SystemDialog(state = state)

    when (state.showingDialog) {
        HomeState.ShowingDialog.Help -> {
            KafkaHelpSheet(
                onDismissRequest = {
                    state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.Closed))
                }
            )
        }

        HomeState.ShowingDialog.NewChatCreator -> {
            when (state.members) {
                AsyncOutcome.Loading -> {}
                is AsyncOutcome.Success<List<Member>> -> {
                    SingleSelectionDialog(
                        options = state.members.value,
                        onConfirm = {
                            state.eventSink(HomeEvents.CreateChat(state.members.value[it].id))
                        },
                        onDismiss = {
                            state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.Closed))
                        },
                        title = stringResource(Res.string.chats_new_chat_dialog_title),
                        subtitle = stringResource(Res.string.chats_new_chat_dialog_subtitle),
                    ) { member, interactionSource, modifier, selected, onClick ->
                        MemberListItem(
                            member = member,
                            modifier = modifier,
                        ) {
                            RadioButton(
                                selected = selected,
                                onClick = onClick,
                                interactionSource = interactionSource,
                            )
                        }
                    }
                }
            }
        }

        else -> {
            /* Empty */
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TopAppBar(
    system: System,
    navTarget: HomeNavTarget?,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    val consumedWindowInsets =
        adaptiveValue(
            compact = WindowInsets(),
            medium = WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal),
        )

    SharedElementTransitionScope {
        AnimatedContent(
            navTarget,
            modifier = modifier.consumeWindowInsets(consumedWindowInsets),
            transitionSpec = {
                ContentTransform(
                    targetContentEnter = fadeIn(),
                    initialContentExit = fadeOut(),
                    sizeTransform = null,
                )
            },
            contentAlignment = Alignment.CenterStart,
        ) { navTarget ->
            ProvideAnimatedTransitionScope(
                animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
                animatedVisibilityScope = this,
            ) {
                when (navTarget) {
                    HomeNavTarget.Overview -> {
                        OverviewTopAppBar(
                            system = system,
                            scrollBehavior = scrollBehavior,
                            onAvatarClick = onAvatarClick,
                        )
                    }

                    HomeNavTarget.Timeline -> {
                        TimelineTopAppBar(
                            system = system,
                            scrollBehavior = scrollBehavior,
                            onAvatarClick = onAvatarClick,
                        )
                    }

                    HomeNavTarget.Chats -> {
                        ChatsTopAppBar(
                            system = system,
                            scrollBehavior = scrollBehavior,
                            onAvatarClick = onAvatarClick,
                        )
                    }

                    HomeNavTarget.Polls -> {
                        PollsTopAppBar(
                            system = system,
                            scrollBehavior = scrollBehavior,
                            onAvatarClick = onAvatarClick,
                        )
                    }

                    else -> {
                        /* Empty */
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun FloatingActionButton(
    lookaheadScope: LookaheadScope,
    modifier: Modifier = Modifier,
    onAddMember: () -> Unit = {},
    onAddChat: () -> Unit = {},
) {
    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }

    FloatingActionButtonMenu(
        modifier = modifier.offset(x = 16.dp, y = 16.dp),
        expanded = fabMenuExpanded,
        button = {
            ToggleFloatingActionButton(
                modifier = Modifier.animateBounds(lookaheadScope = lookaheadScope),
                checked = fabMenuExpanded,
                onCheckedChange = {
                    fabMenuExpanded = !fabMenuExpanded
                },
            ) {
                val imageVector by remember {
                    derivedStateOf {
                        if (checkedProgress > 0.5f) {
                            KafkaIcons.Close
                        } else {
                            KafkaIcons.Add
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center).animateIcon({ checkedProgress }),
                    )
                }
            }
        },
    ) {
        FloatingActionButtonMenuItem(
            onClick = {
                fabMenuExpanded = false
                onAddMember()
            },
            text = {
                Text(stringResource(CommonStrings.common_new_member))
            },
            icon = {
                Icon(
                    KafkaIcons.PersonOutline,
                    contentDescription = null,
                )
            },
        )
        FloatingActionButtonMenuItem(
            onClick = onAddChat,
            text = {
                Text(stringResource(CommonStrings.common_new_chat))
            },
            icon = {
                Icon(
                    KafkaIcons.ChatBubbleOutline,
                    contentDescription = null,
                )
            },
        )
        FloatingActionButtonMenuItem(
            onClick = {},
            text = {
                Text(stringResource(CommonStrings.common_new_poll))
            },
            icon = {
                Icon(
                    KafkaIcons.Poll,
                    contentDescription = null,
                )
            },
        )
    }
}

@Composable
private inline fun NavigationRailScaffold(
    navigationRail: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        content = {
            Box(modifier = Modifier.zIndex(2f)) {
                navigationRail()
            }
            Box(modifier = Modifier.fillMaxSize().zIndex(1f)) {
                content()
            }
        },
    )
}

@Composable
internal fun NavigationRail(
    avatar: String?,
    onAvatarClick: () -> Unit,
    navTarget: HomeNavTarget,
    modifier: Modifier = Modifier,
    onNewNavTarget: (HomeNavTarget) -> Unit = {},
) {
    NavigationRail(modifier = modifier) {
        NavigationRailItem(
            selected = navTarget == HomeNavTarget.Overview,
            onClick = { onNewNavTarget(HomeNavTarget.Overview) },
            icon = {
                Icon(
                    KafkaIcons.DashboardOutline,
                    contentDescription = stringResource(Res.string.home_nav_overview),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeNavTarget.Timeline,
            onClick = { onNewNavTarget(HomeNavTarget.Timeline) },
            icon = {
                Icon(
                    KafkaIcons.Timeline,
                    contentDescription = stringResource(Res.string.home_nav_timeline),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeNavTarget.Chats,
            onClick = { onNewNavTarget(HomeNavTarget.Chats) },
            icon = {
                Icon(
                    KafkaIcons.ChatBubbleOutline,
                    contentDescription = stringResource(Res.string.home_nav_chat),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeNavTarget.Polls,
            onClick = { onNewNavTarget(HomeNavTarget.Polls) },
            icon = {
                Icon(
                    KafkaIcons.Poll,
                    contentDescription = stringResource(Res.string.home_nav_poll),
                )
            },
            alwaysShowLabel = false,
        )
        Spacer(modifier = Modifier.weight(1f))
        MenuAvatarButton(
            avatar = avatar,
            onClick = onAvatarClick,
        )
    }
}

@Composable
internal fun NavigationBar(
    navTarget: HomeNavTarget?,
    modifier: Modifier = Modifier,
    onNewNavTarget: (HomeNavTarget) -> Unit = {},
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = navTarget == HomeNavTarget.Overview,
            onClick = { onNewNavTarget(HomeNavTarget.Overview) },
            icon = {
                Icon(
                    KafkaIcons.DashboardOutline,
                    contentDescription = stringResource(Res.string.home_nav_overview),
                )
            },
            label = {
                Text(
                    stringResource(Res.string.home_nav_overview),
                    fontWeight = FontWeight.Bold,
                )
            },
            alwaysShowLabel = false,
        )
        NavigationBarItem(
            selected = navTarget == HomeNavTarget.Timeline,
            onClick = { onNewNavTarget(HomeNavTarget.Timeline) },
            icon = {
                Icon(
                    KafkaIcons.Timeline,
                    contentDescription = stringResource(Res.string.home_nav_timeline),
                )
            },
            label = {
                Text(
                    stringResource(Res.string.home_nav_timeline),
                    fontWeight = FontWeight.Bold,
                )
            },
            alwaysShowLabel = false,
        )
        NavigationBarItem(
            selected = navTarget == HomeNavTarget.Chats,
            onClick = { onNewNavTarget(HomeNavTarget.Chats) },
            icon = {
                Icon(
                    KafkaIcons.ChatBubbleOutline,
                    contentDescription = stringResource(Res.string.home_nav_chat),
                )
            },
            label = {
                Text(
                    stringResource(Res.string.home_nav_chat),
                    fontWeight = FontWeight.Bold,
                )
            },
            alwaysShowLabel = false,
        )
        NavigationBarItem(
            selected = navTarget == HomeNavTarget.Polls,
            onClick = { onNewNavTarget(HomeNavTarget.Polls) },
            icon = {
                Icon(
                    KafkaIcons.Poll,
                    contentDescription = stringResource(Res.string.home_nav_poll),
                )
            },
            label = {
                Text(
                    stringResource(Res.string.home_nav_poll),
                    fontWeight = FontWeight.Bold,
                )
            },
            alwaysShowLabel = false,
        )
    }
}
