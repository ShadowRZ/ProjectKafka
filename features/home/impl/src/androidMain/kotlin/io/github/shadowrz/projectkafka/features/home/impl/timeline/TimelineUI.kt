package io.github.shadowrz.projectkafka.features.home.impl.timeline

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.features.home.impl.HomeComponent
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.components.MenuAvatarButton
import io.github.shadowrz.projectkafka.features.home.impl.preview.aSystem
import io.github.shadowrz.projectkafka.features.home.impl.timeline.frontlog.FrontLogUI
import io.github.shadowrz.projectkafka.libraries.components.SharedElements
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Add
import io.github.shadowrz.projectkafka.libraries.icons.material.Check
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_system_subtitle
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.home_nav_timeline
import projectkafka.features.home.impl.generated.resources.menu_tooltip

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

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
internal fun TimelineTopAppBar(
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
                stringResource(Res.string.home_nav_timeline),
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
                    tooltip = { PlainTooltip { Text(stringResource(Res.string.menu_tooltip)) } },
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
                MaterialIcons.Add,
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
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TimelineType.entries.forEach {
            FilterChip(
                selected = state.timelineType == it,
                onClick = {
                    state.eventSink(TimelineEvents.ChangeTimelineType(it))
                },
                label = {
                    Text(
                        stringResource(it.desc),
                    )
                },
                leadingIcon = {
                    if (state.timelineType == it) {
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
private fun PreviewTimelineUI(
    @PreviewParameter(TimelineStateProvider::class) state: TimelineState,
) {
    ProjectKafkaPreview {
        TimelineUI(
            system = aSystem(),
            state = state,
        )
    }
}
