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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.zIndex
import androidx.window.core.layout.WindowSizeClass
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanels
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanelsAnimators
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.router.slot.ChildSlot
import com.composables.core.rememberDialogState
import com.eygraber.uri.Uri
import com.slack.circuit.sharedelements.ProvideAnimatedTransitionScope
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsContent
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsFloatingActionButton
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.components.MenuAvatarButton
import io.github.shadowrz.projectkafka.features.home.impl.components.SystemDialog
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewContent
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewFloatingActionButton
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.polls.PollsContent
import io.github.shadowrz.projectkafka.features.home.impl.polls.PollsFloatingActionButton
import io.github.shadowrz.projectkafka.features.home.impl.polls.PollsTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelineContent
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelineFloatingActionButton
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelineTopAppBar
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.components.KafkaHelpSheet
import io.github.shadowrz.projectkafka.libraries.components.PLATFORM_SUPPORTS_PREDICTIVE_BACK
import io.github.shadowrz.projectkafka.libraries.components.pinnedExitUntilCollapsedScrollBehavior
import io.github.shadowrz.projectkafka.libraries.components.predictiveback.defaultPredictiveBackParams
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ChatBubbleOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.DashboardOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.Poll
import io.github.shadowrz.projectkafka.libraries.icons.material.Timeline

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalDecomposeApi::class,
)
@Composable
@ContributesComponent(SystemScope::class)
internal fun HomeUI(
    component: HomeComponent,
    modifier: Modifier = Modifier,
) {
    val slot by component.slot.subscribeAsState()

    ChildPanels(
        modifier = modifier,
        panels = component.panels,
        mainChild = { _ ->
            HomeUI(
                component = component,
                slot = slot,
            )
        },
        detailsChild = { child ->
            ProvideAnimatedTransitionScope(
                animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
                animatedVisibilityScope = this,
            ) {
                DetailContent(child = child)
            }
        },
        secondPanelPlaceholder = {
            Placeholder(slot = slot)
        },
        animators = ChildPanelsAnimators(single = fade() + slide(), dual = fade() to fade()),
        predictiveBackParams = {
            defaultPredictiveBackParams(
                enabled = PLATFORM_SUPPORTS_PREDICTIVE_BACK,
                backHandler = component.backHandler,
                onBack = component::onBack,
            )
        },
    )

    ChildPanelsModeChangedEffect(component::setMode)
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun ChildPanelsModeChangedEffect(setMode: (ChildPanelsMode) -> Unit) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val childPanelsMode =
        if (windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(
                WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND,
            )
        ) {
            ChildPanelsMode.DUAL
        } else {
            ChildPanelsMode.SINGLE
        }

    LaunchedEffect(childPanelsMode, setMode) {
        setMode(childPanelsMode)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalDecomposeApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeUI(
    component: HomeComponent,
    slot: ChildSlot<HomeComponent.MainNavTarget, HomeComponent.MainResolved>,
    modifier: Modifier = Modifier,
) {
    val dialogState = rememberDialogState()
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val useNavigationRail = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    val child = slot.child
    var showHelp by rememberSaveable { mutableStateOf(false) }

    NavigationRailScaffold(
        navigationRail = {
            AnimatedVisibility(
                visible = useNavigationRail,
                enter = slideInHorizontally(initialOffsetX = { -it }),
                exit = slideOutHorizontally(targetOffsetX = { -it }),
            ) {
                NavigationRail(
                    avatar = component.system.avatar,
                    onAvatarClick = { dialogState.visible = true },
                    navTarget = child?.configuration,
                    onNewNavTarget = component::onNewNavTarget,
                )
            }
        },
    ) {
        val scrollBehavior = pinnedExitUntilCollapsedScrollBehavior()

        SharedElementTransitionScope {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        system = component.system,
                        slot = slot,
                        onAvatarClick = { dialogState.visible = true },
                        scrollBehavior = scrollBehavior,
                    )
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = !useNavigationRail,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                    ) {
                        NavigationBar(
                            navTarget = child?.configuration,
                            onNewNavTarget = component::onNewNavTarget,
                        )
                    }
                },
                floatingActionButton = {
                    child?.instance?.let {
                        FloatingActionButton(instance = it)
                    }
                },
                contentWindowInsets =
                    ScaffoldDefaults.contentWindowInsets
                        .exclude(
                            WindowInsets.navigationBars.only(WindowInsetsSides.Vertical),
                        ).exclude(WindowInsets.displayCutout),
            ) { innerPadding ->
                Crossfade(
                    child,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                ) { child ->
                    child?.let {
                        ListContent(
                            instance = it.instance,
                            nestedScrollConnection = scrollBehavior.nestedScrollConnection,
                            onOpenMember = component::openMember,
                        )
                    }
                }
            }
        }
    }

    SystemDialog(
        state = dialogState,
        name = component.system.name,
        description = component.system.description,
        avatar = component.system.avatar,
        onHelp = {
            dialogState.visible = false
            showHelp = true
        },
        onSettings = {},
        onDataManage = {
            dialogState.visible = false
            component.onDataManage()
        },
        onAbout = {
            dialogState.visible = false
            component.onAbout()
        },
        onSwitchSystem = {
            dialogState.visible = false
            component.onSwitchSystem()
        },
    )

    if (showHelp) {
        KafkaHelpSheet(
            onDismissRequest = { showHelp = false },
        )
    }
}

@Composable
private fun DetailContent(
    child: Child.Created<HomeComponent.DetailNavTarget, HomeComponent.DetailResolved>,
    modifier: Modifier = Modifier,
) {
    child.instance.let {
        when (it) {
            is HomeComponent.DetailResolved.MemberProfile -> {
                ComponentUI(
                    modifier = modifier,
                    component = it.component,
                )
            }
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
private fun TopAppBar(
    system: System,
    slot: ChildSlot<HomeComponent.MainNavTarget, HomeComponent.MainResolved>,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val useNavigationRail = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    val consumedWindowInsets =
        if (useNavigationRail) {
            WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
        } else {
            WindowInsets()
        }

    SharedElementTransitionScope {
        AnimatedContent(
            slot.child?.configuration,
            modifier = modifier.consumeWindowInsets(consumedWindowInsets),
            transitionSpec = {
                ContentTransform(
                    targetContentEnter = fadeIn(),
                    initialContentExit = fadeOut(),
                    sizeTransform = null,
                )
            },
            contentAlignment = Alignment.CenterStart,
        ) { configuration ->
            ProvideAnimatedTransitionScope(
                animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
                animatedVisibilityScope = this,
            ) {
                configuration?.let {
                    when (it) {
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
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun FloatingActionButton(
    instance: HomeComponent.MainResolved,
    modifier: Modifier = Modifier,
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val useNavigationRail = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
    val windowInsets =
        if (useNavigationRail) {
            WindowInsets.navigationBars.only(WindowInsetsSides.Vertical)
        } else {
            WindowInsets()
        }

    AnimatedContent(
        instance,
        modifier = modifier.windowInsetsPadding(windowInsets),
        transitionSpec = { fadeIn() togetherWith fadeOut() },
    ) { instance ->
        ProvideAnimatedTransitionScope(
            animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
            animatedVisibilityScope = this,
        ) {
            when (instance) {
                is HomeComponent.MainResolved.Overview -> {
                    OverviewFloatingActionButton(
                        onAddMember = instance.component::onAddMember,
                    )
                }

                is HomeComponent.MainResolved.Timeline -> {
                    TimelineFloatingActionButton()
                }

                is HomeComponent.MainResolved.Chats -> {
                    ChatsFloatingActionButton()
                }

                is HomeComponent.MainResolved.Polls -> {
                    PollsFloatingActionButton()
                }
            }
        }
    }
}

@Composable
private fun ListContent(
    instance: HomeComponent.MainResolved,
    nestedScrollConnection: NestedScrollConnection,
    modifier: Modifier = Modifier,
    onOpenMember: (MemberID) -> Unit = {},
) {
    when (instance) {
        is HomeComponent.MainResolved.Overview -> {
            val state = instance.component.presenter.present()

            OverviewContent(
                modifier = modifier,
                state = state,
                nestedScrollConnection = nestedScrollConnection,
                onMemberClick = onOpenMember,
            )
        }

        is HomeComponent.MainResolved.Timeline -> {
            val state = instance.component.presenter.present()

            TimelineContent(
                state = state,
                modifier = modifier,
            )
        }

        is HomeComponent.MainResolved.Chats -> {
            val state = instance.component.presenter.present()

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
    slot: ChildSlot<HomeComponent.MainNavTarget, HomeComponent.MainResolved>,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        slot.child?.configuration,
        modifier = modifier.fillMaxSize(),
    ) { configuration ->
        configuration?.let {
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
                        stringResource(R.string.chats_empty_detail),
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                    )
                }

                HomeComponent.MainNavTarget.Polls -> {
                    Text(
                        stringResource(R.string.polls_empty_detail),
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
                    MaterialIcons.DashboardOutline,
                    contentDescription = stringResource(R.string.home_nav_overview),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeComponent.MainNavTarget.Timeline,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Timeline) },
            icon = {
                Icon(
                    MaterialIcons.Timeline,
                    contentDescription = stringResource(R.string.home_nav_timeline),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeComponent.MainNavTarget.Chats,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Chats) },
            icon = {
                Icon(
                    MaterialIcons.ChatBubbleOutline,
                    contentDescription = stringResource(R.string.home_nav_chat),
                )
            },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = navTarget == HomeComponent.MainNavTarget.Polls,
            onClick = { onNewNavTarget(HomeComponent.MainNavTarget.Polls) },
            icon = {
                Icon(
                    MaterialIcons.Poll,
                    contentDescription = stringResource(R.string.home_nav_poll),
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
                    MaterialIcons.DashboardOutline,
                    contentDescription = stringResource(R.string.home_nav_overview),
                )
            },
            label = {
                Text(
                    stringResource(R.string.home_nav_overview),
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
                    MaterialIcons.Timeline,
                    contentDescription = stringResource(R.string.home_nav_timeline),
                )
            },
            label = {
                Text(
                    stringResource(R.string.home_nav_timeline),
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
                    MaterialIcons.ChatBubbleOutline,
                    contentDescription = stringResource(R.string.home_nav_chat),
                )
            },
            label = {
                Text(
                    stringResource(R.string.home_nav_chat),
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
                    MaterialIcons.Poll,
                    contentDescription = stringResource(R.string.home_nav_poll),
                )
            },
            label = {
                Text(
                    stringResource(R.string.home_nav_poll),
                    fontWeight = FontWeight.Bold,
                )
            },
            alwaysShowLabel = false,
        )
    }
}
