package io.github.shadowrz.projectkafka.features.home.impl.polls

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import io.github.shadowrz.projectkafka.libraries.icons.material.Poll
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalDecomposeApi::class,
)
@Composable
internal fun PollsUI(
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
            PollsTopAppBar(
                system = system,
                scrollBehavior = scrollBehavior,
                onAvatarClick = onAvatarClick,
            )
        },
        bottomBar = {
            NavigationBar(navTarget = HomeComponent.MainNavTarget.Polls)
        },
        floatingActionButton = {
            PollsFloatingActionButton()
        },
    ) { innerPadding ->
        PollsContent(
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
internal fun PollsTopAppBar(
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
                stringResource(R.string.home_nav_poll),
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
                MenuAvatarButton(
                    avatar = system.avatar,
                    onClick = {
                        onAvatarClick()
                    },
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
internal fun PollsContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Placeholder()
    }
}

@Composable
private fun Placeholder(modifier: Modifier = Modifier) {
    Text(
        "Coming soon!",
        modifier = modifier.fillMaxSize().wrapContentSize().padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun PollsFloatingActionButton(modifier: Modifier = Modifier) {
    SharedElementTransitionScope {
        ExtendedFloatingActionButton(
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
            icon = {
                Icon(
                    MaterialIcons.Poll,
                    contentDescription = null,
                )
            },
            text = {
                Text(
                    stringResource(R.string.polls_new_poll),
                    modifier = Modifier.skipToLookaheadSize(),
                )
            },
        )
    }
}
