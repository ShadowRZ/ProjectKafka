package io.github.shadowrz.projectkafka.features.welcome.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.assets.SharedDrawables
import io.github.shadowrz.projectkafka.libraries.components.KafkaHelpSheet
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.DatabaseOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.GroupAddOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.HelpOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.InfoOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.Translate
import io.github.shadowrz.projectkafka.libraries.localepicker.LocalePicker
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WelcomeUI(
    state: WelcomeState,
    modifier: Modifier = Modifier,
    onCreateSystem: () -> Unit = {},
    onLearnMore: () -> Unit = {},
    onDataManage: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                colors =
                    topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {},
                actions = {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
                        tooltip = {
                            PlainTooltip {
                                Text(
                                    stringResource(CommonStrings.common_data_management),
                                )
                            }
                        },
                        state = rememberTooltipState(),
                    ) {
                        IconButton(onClick = onDataManage) {
                            Icon(
                                MaterialIcons.DatabaseOutline,
                                contentDescription = stringResource(CommonStrings.common_data_management),
                            )
                        }
                    }
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
                        tooltip = {
                            PlainTooltip {
                                Text(
                                    stringResource(CommonStrings.common_change_locale),
                                )
                            }
                        },
                        state = rememberTooltipState(),
                    ) {
                        IconButton(onClick = {
                            state.localePickerState.openLocalePicker()
                        }) {
                            Icon(
                                MaterialIcons.Translate,
                                contentDescription = stringResource(CommonStrings.common_change_locale),
                            )
                        }
                    }
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
                        tooltip = {
                            PlainTooltip {
                                Text(
                                    stringResource(CommonStrings.common_help),
                                )
                            }
                        },
                        state = rememberTooltipState(),
                    ) {
                        IconButton(onClick = {
                            state.eventSink(WelcomeEvents.ShowHelp(true))
                        }) {
                            Icon(
                                MaterialIcons.HelpOutline,
                                contentDescription = stringResource(CommonStrings.common_help),
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
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
    ) { innerPadding ->
        TopContent(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .verticalScroll(
                        rememberScrollState(),
                    ),
        )
    }

    LocalePicker(state = state.localePickerState)

    if (state.showHelp) {
        KafkaHelpSheet(
            onDismissRequest = {
                state.eventSink(WelcomeEvents.ShowHelp(false))
            },
        )
    }
}

@Composable
private fun TopContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(SharedDrawables.welcome),
                contentDescription = null,
                modifier = Modifier.size(288.dp).padding(32.dp),
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                stringResource(CommonStrings.app_name),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Text(
                stringResource(R.string.welcome_subtitle),
                style = MaterialTheme.typography.titleMedium,
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
    Column(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onCreateSystem,
            modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
        ) {
            Icon(
                MaterialIcons.GroupAddOutline,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(
                    R.string.welcome_create_system,
                ),
            )
        }
        FilledTonalButton(
            onClick = onLearnMore,
            modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
        ) {
            Icon(
                MaterialIcons.InfoOutline,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(
                    R.string.welcome_learn_more,
                ),
            )
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun PreviewWelcomeUI(
    @PreviewParameter(WelcomeStateProvider::class) state: WelcomeState,
) {
    ProjectKafkaPreview {
        WelcomeUI(state = state)
    }
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
