package io.github.shadowrz.projectkafka.features.welcome.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.Button
import io.github.shadowrz.projectkafka.designsystem.ButtonColumn
import io.github.shadowrz.projectkafka.designsystem.FilledTonalButton
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.KawaiiLogo
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.GroupAddOutline
import io.github.shadowrz.projectkafka.designsystem.icons.InfoOutline
import io.github.shadowrz.projectkafka.designsystem.pages.WelcomePage
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.app_name
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.welcome.impl.generated.resources.Res
import projectkafka.features.welcome.impl.generated.resources.welcome_create_system
import projectkafka.features.welcome.impl.generated.resources.welcome_learn_more
import projectkafka.features.welcome.impl.generated.resources.welcome_subtitle

@Composable
internal fun WelcomeUI(
    state: WelcomeState,
    modifier: Modifier = Modifier,
    onCreateSystem: () -> Unit = {},
    onLearnMore: () -> Unit = {},
    onDataManage: () -> Unit = {},
) {
    WelcomePage(
        modifier = modifier,
        content = {
            TopContent()
        },
        footer = {
            BottomContent(
                modifier =
                    Modifier
                        .windowInsetsPadding(
                            WindowInsets.navigationBars
                                .add(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)),
                        ).imePadding(),
                onCreateSystem = onCreateSystem,
                onLearnMore = onLearnMore,
            )
        },
    )
}

@Composable
private fun TopContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        KawaiiLogo(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                stringResource(CommonStrings.app_name),
                color = KafkaTheme.materialColors.primary,
                style = KafkaTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Text(
                stringResource(Res.string.welcome_subtitle),
                style = KafkaTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun BottomContent(
    modifier: Modifier = Modifier,
    onCreateSystem: () -> Unit = {},
    onLearnMore: () -> Unit = {},
) {
    ButtonColumn(modifier = modifier.padding(16.dp)) {
        Button(
            stringResource(Res.string.welcome_create_system),
            onClick = onCreateSystem,
            modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
            leadingIcon = KafkaIcons.GroupAddOutline,
        )
        FilledTonalButton(
            stringResource(Res.string.welcome_learn_more),
            onClick = onLearnMore,
            modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
            leadingIcon = KafkaIcons.InfoOutline,
        )
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewWelcomeUI(
    @PreviewParameter(WelcomeStateProvider::class) state: WelcomeState,
) = KafkaPreview {
    WelcomeUI(state = state)
}

@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun WelcomeUI(
    component: WelcomeComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    WelcomeUI(
        state = state,
        modifier = modifier,
        onCreateSystem = {
            component.callback.onCreateSystem()
        },
        onLearnMore = {
            component.callback.onLearnMore()
        },
        onDataManage = {
            component.callback.onDataManage()
        },
    )
}
