package io.github.shadowrz.projectkafka.features.home.impl.polls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.features.home.impl.HomeNavTarget
import io.github.shadowrz.projectkafka.features.home.impl.NavigationBar
import io.github.shadowrz.projectkafka.features.home.impl.components.BaseTopAppBar
import io.github.shadowrz.projectkafka.libraries.data.api.System
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.home_nav_poll

@Composable
internal fun PollsUI(
    system: System,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    SharedTransitionScope {
        Scaffold(
            modifier = modifier.then(it),
            topBar = {
                AnimatedVisibility(visible = true) {
                    PollsTopAppBar(
                        system = system,
                        onAvatarClick = onAvatarClick,
                        sharedTransitionScope = this@SharedTransitionScope,
                        animatedVisibilityScope = this,
                    )
                }
            },
            bottomBar = {
                NavigationBar(navTarget = HomeNavTarget.Polls)
            },
        ) { innerPadding ->
            PollsContent(modifier = Modifier.fillMaxSize().padding(innerPadding).imePadding())
        }
    }
}

@Composable
@NonRestartableComposable
internal fun PollsTopAppBar(
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
        title = stringResource(Res.string.home_nav_poll),
        scrollBehavior = scrollBehavior,
        onAvatarClick = onAvatarClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
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
