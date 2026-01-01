package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ArrowForward
import io.github.shadowrz.projectkafka.libraries.profile.components.SelectAvatar
import io.github.shadowrz.projectkafka.libraries.profile.components.SelectCover
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_avatar
import io.github.shadowrz.projectkafka.libraries.strings.common_continue
import io.github.shadowrz.projectkafka.libraries.strings.common_cover
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.createsystem.impl.generated.resources.Res
import projectkafka.features.createsystem.impl.generated.resources.adddetails_description
import projectkafka.features.createsystem.impl.generated.resources.adddetails_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDetailsUI(
    state: AddDetailsState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors =
                    topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {},
            )
        },
        bottomBar = {
            BottomContent(
                state = state,
                modifier =
                    Modifier
                        .windowInsetsPadding(
                            WindowInsets.navigationBars
                                .add(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)),
                        ).imePadding(),
            )
        },
    ) { innerPadding ->
        TopContent(
            state = state,
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
}

@Composable
private fun TopContent(
    state: AddDetailsState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().widthIn(max = 360.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                stringResource(Res.string.adddetails_title),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Text(
                stringResource(Res.string.adddetails_description, state.systemName),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }
        Column(
            modifier = Modifier.widthIn(max = 360.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                stringResource(CommonStrings.common_cover),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            ElevatedCard {
                SelectCover(
                    state = state.coverState,
                )
            }
            Text(
                stringResource(CommonStrings.common_avatar),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            SelectAvatar(
                state = state.avatarState,
            )
        }
    }
}

@Composable
private fun BottomContent(
    state: AddDetailsState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            enabled = !state.loading,
            onClick = {
                state.eventSink(AddDetailsEvents.CreateSystem)
            },
            modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
        ) {
            if (state.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
            } else {
                Icon(
                    MaterialIcons.ArrowForward,
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(CommonStrings.common_continue),
            )
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun PreviewAddDetailsUI(
    @PreviewParameter(AddDetailsStateProvider::class) state: AddDetailsState,
) {
    ProjectKafkaPreview {
        AddDetailsUI(state = state)
    }
}
