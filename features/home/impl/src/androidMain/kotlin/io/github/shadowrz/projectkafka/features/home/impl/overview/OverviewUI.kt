package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.features.home.impl.HomeComponent
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.R
import io.github.shadowrz.projectkafka.features.home.impl.components.MenuAvatarButton
import io.github.shadowrz.projectkafka.features.home.impl.overview.members.MembersUI
import io.github.shadowrz.projectkafka.features.home.impl.overview.tools.ToolsUI
import io.github.shadowrz.projectkafka.features.home.impl.preview.aSystem
import io.github.shadowrz.projectkafka.libraries.components.SharedElements
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Add
import io.github.shadowrz.projectkafka.libraries.icons.material.ChatBubbleOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.Check
import io.github.shadowrz.projectkafka.libraries.icons.material.Close
import io.github.shadowrz.projectkafka.libraries.icons.material.PersonOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.Poll
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.app_name
import io.github.shadowrz.projectkafka.libraries.strings.common_new_chat
import io.github.shadowrz.projectkafka.libraries.strings.common_new_member
import io.github.shadowrz.projectkafka.libraries.strings.common_new_poll
import io.github.shadowrz.projectkafka.libraries.strings.common_system_subtitle
import org.jetbrains.compose.resources.stringResource

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
private fun OverviewUI(
    system: System,
    state: OverviewState,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier =
            modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            OverviewTopAppBar(
                system = system,
                scrollBehavior = scrollBehavior,
                onAvatarClick = onAvatarClick,
            )
        },
        bottomBar = {
            NavigationBar(navTarget = HomeComponent.MainNavTarget.Overview)
        },
        floatingActionButton = {
            OverviewFloatingActionButton()
        },
    ) { innerPadding ->
        OverviewContent(
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
internal fun OverviewTopAppBar(
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
                stringResource(CommonStrings.app_name),
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

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
internal fun OverviewFloatingActionButton(
    modifier: Modifier = Modifier,
    onAddMember: () -> Unit = {},
) {
    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }

    SharedElementTransitionScope {
        FloatingActionButtonMenu(
            modifier = modifier.offset(x = 16.dp, y = 16.dp),
            expanded = fabMenuExpanded,
            button = {
                ToggleFloatingActionButton(
                    checked = fabMenuExpanded,
                    onCheckedChange = {
                        fabMenuExpanded = !fabMenuExpanded
                    },
                    modifier =
                        Modifier
                            .sharedElement(
                                sharedContentState =
                                    rememberSharedContentState(
                                        SharedElements.FloatingActionButton,
                                    ),
                                animatedVisibilityScope =
                                    requireAnimatedScope(
                                        SharedElementTransitionScope.AnimatedScope.Navigation,
                                    ),
                            ),
                ) {
                    val imageVector by remember {
                        derivedStateOf {
                            if (checkedProgress > 0.5f) {
                                MaterialIcons.Close
                            } else {
                                MaterialIcons.Add
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
                        MaterialIcons.PersonOutline,
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
                        MaterialIcons.ChatBubbleOutline,
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
                        MaterialIcons.Poll,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@Composable
internal fun OverviewContent(
    state: OverviewState,
    modifier: Modifier = Modifier,
    onMemberClick: (MemberID) -> Unit = {},
) {
    Column(modifier = modifier) {
        FilterChips(
            state = state,
        )
        Crossfade(state.overviewSection) {
            when (it) {
                OverviewSection.Members -> {
                    MembersUI(
                        state = state.membersState,
                        onNewMember = {
                            state.eventSink(OverviewEvents.AddMember)
                        },
                        onMemberClick = onMemberClick,
                    )
                }

                OverviewSection.Tools -> {
                    ToolsUI(
                        onFronterIndicator = {
                            state.eventSink(OverviewEvents.LaunchFronterIndicator)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterChips(
    state: OverviewState,
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
        OverviewSection.entries.forEach {
            FilterChip(
                selected = state.overviewSection == it,
                onClick = {
                    state.eventSink(OverviewEvents.ChangeOverviewSection(it))
                },
                label = {
                    Text(
                        stringResource(it.desc),
                    )
                },
                leadingIcon = {
                    if (state.overviewSection == it) {
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

@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun PreviewOverviewUI(
    @PreviewParameter(OverviewStateProvider::class) state: OverviewState,
) {
    ProjectKafkaPreview {
        OverviewUI(
            system = aSystem(),
            state = state,
        )
    }
}
