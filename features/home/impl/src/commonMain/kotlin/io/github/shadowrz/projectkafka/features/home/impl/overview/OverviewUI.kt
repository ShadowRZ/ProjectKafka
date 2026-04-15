package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.designsystem.FilterChip
import io.github.shadowrz.projectkafka.designsystem.FilterRow
import io.github.shadowrz.projectkafka.designsystem.FloatingActionButtonMenu
import io.github.shadowrz.projectkafka.designsystem.FloatingActionButtonMenuItem
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.ToggleFloatingActionButton
import io.github.shadowrz.projectkafka.designsystem.ToggleFloatingActionButtonDefaults.animateIcon
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.designsystem.icons.Add
import io.github.shadowrz.projectkafka.designsystem.icons.ChatBubbleOutline
import io.github.shadowrz.projectkafka.designsystem.icons.Close
import io.github.shadowrz.projectkafka.designsystem.icons.PersonOutline
import io.github.shadowrz.projectkafka.designsystem.icons.Poll
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.features.home.impl.HomeComponent
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.SharedElements
import io.github.shadowrz.projectkafka.features.home.impl.components.BaseTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.overview.members.MembersUI
import io.github.shadowrz.projectkafka.features.home.impl.overview.tools.ToolsUI
import io.github.shadowrz.projectkafka.features.home.impl.preview.aSystem
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.app_name
import io.github.shadowrz.projectkafka.libraries.strings.common_new_chat
import io.github.shadowrz.projectkafka.libraries.strings.common_new_member
import io.github.shadowrz.projectkafka.libraries.strings.common_new_poll
import org.jetbrains.compose.resources.stringResource

@OptIn(
    ExperimentalSharedTransitionApi::class,
)
@Composable
private fun OverviewUI(
    system: System,
    state: OverviewState,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            OverviewTopAppBar(
                system = system,
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

@Composable
@NonRestartableComposable
internal fun OverviewTopAppBar(
    system: System,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onAvatarClick: () -> Unit = {},
) {
    BaseTopAppBar(
        modifier = modifier,
        system = system,
        title = stringResource(CommonStrings.app_name),
        scrollBehavior = scrollBehavior,
        onAvatarClick = onAvatarClick,
    )
}

@OptIn(
    ExperimentalSharedTransitionApi::class,
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
    FilterRow(modifier = modifier) {
        OverviewSection.entries.forEach {
            FilterChip(
                selected = state.overviewSection == it,
                onClick = {
                    state.eventSink(OverviewEvents.ChangeOverviewSection(it))
                },
                label = stringResource(it.desc),
                leadingIcon = it.imageVector,
            )
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewOverviewUI(
    @PreviewParameter(OverviewStateProvider::class) state: OverviewState,
) = KafkaPreview {
    OverviewUI(
        system = aSystem(),
        state = state,
    )
}
