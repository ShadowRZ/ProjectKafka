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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.designsystem.icons.Poll
import io.github.shadowrz.projectkafka.features.home.impl.HomeComponent
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.SharedElements
import io.github.shadowrz.projectkafka.features.home.impl.components.BaseTopAppBar
import io.github.shadowrz.projectkafka.libraries.data.api.System
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.home_nav_poll
import projectkafka.features.home.impl.generated.resources.polls_new_poll

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
    Scaffold(
        modifier = modifier,
        topBar = {
            PollsTopAppBar(
                system = system,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
internal fun PollsTopAppBar(
    system: System,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onAvatarClick: () -> Unit = {},
) {
    BaseTopAppBar(
        modifier = modifier,
        system = system,
        title = stringResource(Res.string.home_nav_poll),
        scrollBehavior = scrollBehavior,
        onAvatarClick = onAvatarClick,
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
        color = KafkaTheme.materialColors.onSurfaceVariant,
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
                    KafkaIcons.Poll,
                    contentDescription = null,
                )
            },
            text = {
                Text(
                    stringResource(Res.string.polls_new_poll),
                    modifier = Modifier.skipToLookaheadSize(),
                )
            },
        )
    }
}
