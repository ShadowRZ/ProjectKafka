package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Button
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TextField
import io.github.shadowrz.projectkafka.designsystem.icons.ArrowForward
import io.github.shadowrz.projectkafka.designsystem.pages.FlowStepPage
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_continue
import io.github.shadowrz.projectkafka.libraries.strings.common_system_name
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.createsystem.impl.generated.resources.Res
import projectkafka.features.createsystem.impl.generated.resources.createsystem_create_systems_later
import projectkafka.features.createsystem.impl.generated.resources.createsystem_description
import projectkafka.features.createsystem.impl.generated.resources.createsystem_system_name_cant_empty
import projectkafka.features.createsystem.impl.generated.resources.createsystem_title

@Composable
internal fun CreateSystemUI(
    state: CreateSystemState,
    modifier: Modifier = Modifier,
    onContinue: (String) -> Unit = {},
) {
    FlowStepPage(
        modifier = modifier,
        title = stringResource(Res.string.createsystem_title),
        subtitle = stringResource(Res.string.createsystem_description),
        scrollable = true,
        buttons = {
            Button(
                enabled = state.valid,
                text = stringResource(CommonStrings.common_continue),
                leadingIcon = KafkaIcons.ArrowForward,
                onClick = { onContinue(state.textFieldState.text.toString()) },
                modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(top = 48.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextField(
                    state = state.textFieldState,
                    modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
                    label = stringResource(CommonStrings.common_system_name),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    isError = !state.valid,
                    supportingText = if (!state.valid) {
                        stringResource(Res.string.createsystem_system_name_cant_empty)
                    } else {
                        null
                    },
                )
                Text(
                    stringResource(Res.string.createsystem_create_systems_later),
                    style = KafkaTheme.typography.bodySmall,
                    color = KafkaTheme.materialColors.secondary,
                    textAlign = TextAlign.Center,
                )
            }
        },
    )
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewCreateSystemUI(
    @PreviewParameter(CreateSystemStateProvider::class) state: CreateSystemState,
) = KafkaPreview {
    CreateSystemUI(state = state)
}
