package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import io.github.shadowrz.projectkafka.designsystem.FilterChip
import io.github.shadowrz.projectkafka.designsystem.FilterRow
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.designsystem.preview.PreviewKafka
import io.github.shadowrz.projectkafka.features.home.impl.HomeNavTarget
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.components.BaseTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.overview.members.MembersUI
import io.github.shadowrz.projectkafka.features.home.impl.overview.tools.ToolsUI
import io.github.shadowrz.projectkafka.features.home.impl.preview.aSystem
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.app_name
import org.jetbrains.compose.resources.stringResource

@Composable
private fun OverviewUI(
    system: System,
    state: OverviewState,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    SharedTransitionScope {
        Scaffold(
            modifier = modifier.then(it),
            topBar = {
                AnimatedVisibility(visible = true) {
                    OverviewTopAppBar(
                        system = system,
                        onAvatarClick = onAvatarClick,
                        sharedTransitionScope = this@SharedTransitionScope,
                        animatedVisibilityScope = this,
                    )
                }
            },
            bottomBar = {
                NavigationBar(navTarget = HomeNavTarget.Overview)
            },
        ) { innerPadding ->
            OverviewContent(
                state = state,
                modifier = Modifier.fillMaxSize().padding(innerPadding).imePadding(),
            )
        }
    }
}

@Composable
@NonRestartableComposable
internal fun OverviewTopAppBar(
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
        title = stringResource(CommonStrings.app_name),
        scrollBehavior = scrollBehavior,
        onAvatarClick = onAvatarClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@Composable
internal fun OverviewContent(
    state: OverviewState,
    modifier: Modifier = Modifier,
    onMemberClick: (MemberID) -> Unit = {},
) {
    Column(modifier = modifier) {
        FilterChips(state = state)
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
                        }
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

@PreviewKafka
@Composable
internal fun PreviewOverviewUI(@PreviewParameter(OverviewStateProvider::class) state: OverviewState) = KafkaPreview {
    OverviewUI(
        system = aSystem(),
        state = state,
    )
}
