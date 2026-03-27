package io.github.shadowrz.projectkafka.features.home.impl.timeline

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.designsystem.FilterChip
import io.github.shadowrz.projectkafka.designsystem.FilterRow
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.Add
import io.github.shadowrz.projectkafka.designsystem.icons.Check
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.features.home.impl.HomeComponent
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.SharedElements
import io.github.shadowrz.projectkafka.features.home.impl.components.BaseTopAppBar
import io.github.shadowrz.projectkafka.features.home.impl.preview.aSystem
import io.github.shadowrz.projectkafka.features.home.impl.timeline.frontlog.FrontLogUI
import io.github.shadowrz.projectkafka.libraries.data.api.System
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.home_nav_timeline

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
private fun TimelineUI(
    system: System,
    state: TimelineState,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier =
            modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TimelineTopAppBar(
                system = system,
                scrollBehavior = scrollBehavior,
                onAvatarClick = onAvatarClick,
            )
        },
        bottomBar = {
            NavigationBar(navTarget = HomeComponent.MainNavTarget.Timeline)
        },
        floatingActionButton = {
            TimelineFloatingActionButton()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
internal fun TimelineTopAppBar(
    system: System,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun TimelineFloatingActionButton(modifier: Modifier = Modifier) {
    SharedElementTransitionScope {
        FloatingActionButton(
            onClick = {},
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
        ) {
            Icon(
                KafkaIcons.Add,
                contentDescription = null,
            )
        }
    }
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
                leadingIcon = {
                    if (state.timelineType == it) {
                        Icon(
                            KafkaIcons.Check,
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
internal fun PreviewTimelineUI(
    @PreviewParameter(TimelineStateProvider::class) state: TimelineState,
) = KafkaPreview {
    TimelineUI(
        system = aSystem(),
        state = state,
    )
}
