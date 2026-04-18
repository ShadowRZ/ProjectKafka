package io.github.shadowrz.projectkafka.features.home.impl.timeline

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import io.github.shadowrz.projectkafka.designsystem.FilterChip
import io.github.shadowrz.projectkafka.designsystem.FilterRow
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.features.home.impl.HomeComponent
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.components.BaseTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.preview.aSystem
import io.github.shadowrz.projectkafka.features.home.impl.timeline.frontlog.FrontLogUI
import io.github.shadowrz.projectkafka.libraries.data.api.System
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.home_nav_timeline

@Composable
private fun TimelineUI(
    system: System,
    state: TimelineState,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TimelineTopAppBar(
                system = system,
                onAvatarClick = onAvatarClick,
            )
        },
        bottomBar = {
            NavigationBar(navTarget = HomeComponent.MainNavTarget.Timeline)
        },
    ) { innerPadding ->
        TimelineContent(
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
internal fun TimelineTopAppBar(
    system: System,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onAvatarClick: () -> Unit = {},
) {
    BaseTopAppBar(
        modifier = modifier,
        system = system,
        title = stringResource(Res.string.home_nav_timeline),
        scrollBehavior = scrollBehavior,
        onAvatarClick = onAvatarClick,
    )
}

@Composable
internal fun TimelineContent(
    state: TimelineState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        FilterChips(state = state)
        Crossfade(state.timelineType) {
            when (it) {
                TimelineType.FrontLog -> {
                    FrontLogUI(
                        state = state.frontLogsState,
                    )
                }

                else -> {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            "Coming Soon!",
                            modifier = Modifier.fillMaxSize().wrapContentSize(),
                        )
                    }
                }
//                TimelineType.Activity -> TODO()
//                TimelineType.QuickNotes -> TODO()
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Coming Soon!",
                modifier = Modifier.fillMaxSize().wrapContentSize(),
            )
        }
    }
}

@Composable
private fun FilterChips(
    state: TimelineState,
    modifier: Modifier = Modifier,
) {
    FilterRow(modifier = modifier) {
        TimelineType.entries.forEach {
            FilterChip(
                selected = state.timelineType == it,
                onClick = {
                    state.eventSink(TimelineEvents.ChangeTimelineType(it))
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
internal fun PreviewTimelineUI(
    @PreviewParameter(TimelineStateProvider::class) state: TimelineState,
) = KafkaPreview {
    TimelineUI(
        system = aSystem(),
        state = state,
    )
}
