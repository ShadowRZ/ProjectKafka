package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.eygraber.uri.Uri
import com.slack.circuit.sharedelements.ProvideAnimatedTransitionScope
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroContent
import io.github.shadowrz.projectkafka.designsystem.ChildPanels
import io.github.shadowrz.projectkafka.designsystem.FloatingActionButtonMenu
import io.github.shadowrz.projectkafka.designsystem.FloatingActionButtonMenuItem
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.NavigationBar
import io.github.shadowrz.projectkafka.designsystem.NavigationBarItem
import io.github.shadowrz.projectkafka.designsystem.NavigationRail
import io.github.shadowrz.projectkafka.designsystem.NavigationRailItem
import io.github.shadowrz.projectkafka.designsystem.Scaffold
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
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsContent
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.components.MenuAvatarButton
import io.github.shadowrz.projectkafka.features.home.impl.components.SystemDialog
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewContent
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.polls.PollsContent
import io.github.shadowrz.projectkafka.features.home.impl.polls.PollsTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelineContent
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelineTopAppBar
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.kafkaui.KafkaHelpSheet
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_new_chat
import io.github.shadowrz.projectkafka.libraries.strings.common_new_member
import io.github.shadowrz.projectkafka.libraries.strings.common_new_poll
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.chats_empty_detail
import projectkafka.features.home.impl.generated.resources.home_nav_chat
import projectkafka.features.home.impl.generated.resources.home_nav_overview
import projectkafka.features.home.impl.generated.resources.home_nav_poll
import projectkafka.features.home.impl.generated.resources.home_nav_timeline
import projectkafka.features.home.impl.generated.resources.polls_empty_detail

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalDecomposeApi::class,
)
@Composable
@HanekokoroInject.ContributesRenderer(SystemScope::class)
internal fun HomeUI(
    component: HomeComponent,
    modifier: Modifier = Modifier,
) {
    val slot by component.slot.subscribeAsState()
    val state = component.presenter.present()

    ChildPanels(
        modifier = modifier,
        panels = component.panels,
        backHandler = component.backHandler,
        onBack = component::onBack,
        mainChild = { _ ->
            HomeUI(
                state = state,
                navTarget = slot.child?.configuration,
                onNewNavTarget = component::onNewNavTarget,
                floatingActionButton = {
                    FloatingActionButton(
                        onAddMember = component::onAddMember,
                    )
                },
            ) { innerPadding ->
                AnimatedContent(
                    slot.child,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                ) { child ->
                    ProvideAnimatedTransitionScope(
                        animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
                        animatedVisibilityScope = this,
                    ) {
                        child?.instance?.ListContent(
                            onOpenMember = component::onOpenMember,
                        )
                    }
                }
            }
        },
        detailsChild = { child ->
            ProvideAnimatedTransitionScope(
                animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
                animatedVisibilityScope = this,
            ) {
                child.instance.DetailContent()
            }
        },
        secondPanelPlaceholder = {
            Placeholder(navTarget = slot.child?.configuration)
        },
    )

    ChildPanelsModeChangedEffect(component::setMode)
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun ChildPanelsModeChangedEffect(setMode: (ChildPanelsMode) -> Unit) {
    val childPanelsMode = adaptiveValue(
        compact = ChildPanelsMode.SINGLE,
        medium = ChildPanelsMode.SINGLE,
        expanded = ChildPanelsMode.DUAL,
    )

    LaunchedEffect(childPanelsMode, setMode) {
        setMode(childPanelsMode)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalDecomposeApi::class)
@Composable
private fun HomeUI(
    state: HomeState,
    navTarget: HomeComponent.MainNavTarget?,
    modifier: Modifier = Modifier,
    onNewNavTarget: (HomeComponent.MainNavTarget) -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
) {
    val useNavigationRail = AdaptiveLayout.useNavigationRail()

    NavigationRailScaffold(
        navigationRail = {
            AnimatedVisibility(
                visible = useNavigationRail,
                enter = slideInHorizontally(initialOffsetX = { -it }),
                exit = slideOutHorizontally(targetOffsetX = { -it }),
            ) {
                NavigationRail(
                    avatar = state.system.avatar,
                    navTarget = navTarget,
                    onNewNavTarget = onNewNavTarget,
                    onAvatarClick = {
                        state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.SystemMenu))
                    },
                )
            }
        },
    ) {
        val scrollBehavior = pinnedExitUntilCollapsedScrollBehavior()

        SharedElementTransitionScope {
            Scaffold(
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                        .exclude(
                            WindowInsets.navigationBars.only(WindowInsetsSides.Vertical),
                        ).exclude(WindowInsets.displayCutout),
            ) { innerPadding ->
                content(innerPadding)
            }
        }
    }

    SystemDialog(
        state = state,
        dialogState = state.dialogState,
    )

    when (state.showingDialog) {
        HomeState.ShowingDialog.Help -> {
            KafkaHelpSheet(
                onDismissRequest = {
                    state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.Closed))
                },
            )
        }

        else -> { /* Empty */ }
    }
}

@Composable
private fun HomeComponent.DetailResolved.DetailContent(modifier: Modifier = Modifier) {
    when (this) {
        is HomeComponent.DetailResolved.MemberProfile -> {
            HanekokoroContent(
                modifier = modifier,
                component = component,
            )
        }
    }
}

@OptIn(
    ExperimentalSharedTransitionApi::class,
)
@Composable
private fun TopAppBar(
    system: System,
    navTarget: HomeComponent.MainNavTarget?,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    val consumedWindowInsets = adaptiveValue(
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
                    HomeComponent.MainNavTarget.Overview -> {
                        OverviewTopAppBar(
                            system = system,
                            scrollBehavior = scrollBehavior,
                            onAvatarClick = onAvatarClick,
                        )
                    }

                    HomeComponent.MainNavTarget.Timeline -> {
                        TimelineTopAppBar(
                            system = system,
                            scrollBehavior = scrollBehavior,
                            onAvatarClick = onAvatarClick,
                        )
                    }

                    HomeComponent.MainNavTarget.Chats -> {
                        ChatsTopAppBar(
                            system = system,
                            scrollBehavior = scrollBehavior,
                            onAvatarClick = onAvatarClick,
                        )
                    }

                    HomeComponent.MainNavTarget.Polls -> {
                        PollsTopAppBar(
                            system = system,
                            scrollBehavior = scrollBehavior,
                            onAvatarClick = onAvatarClick,
                        )
                    }

                    else -> { /* Empty */ }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun FloatingActionButton(
    modifier: Modifier = Modifier,
    onAddMember: () -> Unit = {},
) {
    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }

    FloatingActionButtonMenu(
        modifier = modifier.offset(x = 16.dp, y = 16.dp),
        expanded = fabMenuExpanded,
        button = {
            ToggleFloatingActionButton(
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
                        modifier =
                            Modifier
                                .align(
                                    Alignment.Center,
                                ).animateIcon({ checkedProgress }),
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
                Text(
                    stringResource(CommonStrings.common_new_member),
                )
            },
            icon = {
                Icon(
                    KafkaIcons.PersonOutline,
                    contentDescription = null,
                )
            },
        )
        FloatingActionButtonMenuItem(
            onClick = {},
            text = {
                Text(
                    stringResource(CommonStrings.common_new_chat),
                )
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
                Text(
                    stringResource(CommonStrings.common_new_poll),
                )
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
private fun HomeComponent.MainResolved.ListContent(
    modifier: Modifier = Modifier,
    onOpenMember: (MemberID) -> Unit = {},
) {
    when (this) {
        is HomeComponent.MainResolved.Overview -> {
            val state = component.presenter.present()

            OverviewContent(
                modifier = modifier,
                state = state,
                onMemberClick = onOpenMember,
            )
        }

        is HomeComponent.MainResolved.Timeline -> {
            val state = component.presenter.present()

            TimelineContent(
                state = state,
                modifier = modifier,
            )
        }

        is HomeComponent.MainResolved.Chats -> {
            val state = component.presenter.present()

            ChatsContent(
                state = state,
                modifier = modifier,
            )
        }

        is HomeComponent.MainResolved.Polls -> {
            PollsContent(
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Placeholder(
    navTarget: HomeComponent.MainNavTarget?,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        navTarget,
        modifier = modifier.fillMaxSize(),
    ) { navTarget ->
        navTarget?.let {
            when (it) {
                HomeComponent.MainNavTarget.Overview -> {
                    Text(
                        "Coming soon!",
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                    )
                }

                HomeComponent.MainNavTarget.Timeline -> {
                    Text(
                        "Coming soon!",
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                    )
                }

                HomeComponent.MainNavTarget.Chats -> {
                    Text(
                        stringResource(Res.string.chats_empty_detail),
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                    )
                }

                HomeComponent.MainNavTarget.Polls -> {
                    Text(
                        stringResource(Res.string.polls_empty_detail),
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                    )
                }
            }
        }
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
            Box(
                modifier = Modifier.zIndex(2f),
            ) {
                navigationRail()
            }
            Box(
                modifier = Modifier.fillMaxSize().zIndex(1f),
            ) {
                content()
            }
        },
    )
}

@Composable
internal fun NavigationRail(
    avatar: Uri?,
    onAvatarClick: () -> Unit,
    navTarget: HomeComponent.MainNavTarget?,
    modifier: Modifier = Modifier,
    onNewNavTarget: (HomeComponent.MainNavTarget) -> Unit = {},
) {
    NavigationRail(
        modifier = modifier,
    ) {
        NavigationRailItem(
            selected = navTarget == HomeComponent.MainNavTarget.Overview,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Overview) },
            icon = {
                Icon(
                    KafkaIcons.DashboardOutline,
                    contentDescription = stringResource(Res.string.home_nav_overview),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeComponent.MainNavTarget.Timeline,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Timeline) },
            icon = {
                Icon(
                    KafkaIcons.Timeline,
                    contentDescription = stringResource(Res.string.home_nav_timeline),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeComponent.MainNavTarget.Chats,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Chats) },
            icon = {
                Icon(
                    KafkaIcons.ChatBubbleOutline,
                    contentDescription = stringResource(Res.string.home_nav_chat),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeComponent.MainNavTarget.Polls,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Polls) },
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
    navTarget: HomeComponent.MainNavTarget?,
    modifier: Modifier = Modifier,
    onNewNavTarget: (HomeComponent.MainNavTarget) -> Unit = {},
) {
    NavigationBar(
        modifier = modifier,
    ) {
        NavigationBarItem(
            selected = navTarget == HomeComponent.MainNavTarget.Overview,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Overview) },
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
            selected = navTarget == HomeComponent.MainNavTarget.Timeline,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Timeline) },
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
            selected = navTarget == HomeComponent.MainNavTarget.Chats,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Chats) },
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
            selected = navTarget == HomeComponent.MainNavTarget.Polls,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Polls) },
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
