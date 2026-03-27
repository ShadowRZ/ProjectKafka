package io.github.shadowrz.projectkafka.features.ftue.impl.notification

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Button
import io.github.shadowrz.projectkafka.designsystem.FilledTonalButton
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.icons.ArrowForward
import io.github.shadowrz.projectkafka.designsystem.icons.Check
import io.github.shadowrz.projectkafka.designsystem.pages.FlowStepPage
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_skip
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.ftue.impl.generated.resources.Res
import projectkafka.features.ftue.impl.generated.resources.ftue_notification_enable
import projectkafka.features.ftue.impl.generated.resources.ftue_notification_subtitle
import projectkafka.features.ftue.impl.generated.resources.ftue_notification_title

@Composable
internal fun NotificationUI(
    state: NotificationState,
    modifier: Modifier = Modifier,
) {
    FlowStepPage(
        modifier = modifier,
        title = stringResource(Res.string.ftue_notification_title),
        subtitle = stringResource(Res.string.ftue_notification_subtitle),
        scrollable = true,
        buttons = {
            Button(
                text = stringResource(Res.string.ftue_notification_enable),
                leadingIcon = KafkaIcons.Check,
                onClick = {
                    state.eventSink(NotificationEvents.RequestNotification)
                },
                modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
            )
            FilledTonalButton(
                text = stringResource(CommonStrings.common_skip),
                leadingIcon = KafkaIcons.ArrowForward,
                onClick = {
                    state.eventSink(NotificationEvents.SkipNotification)
                },
                modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
            )
        },
        content = {},
    )
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewNotificationUI() =
    KafkaPreview {
        NotificationUI(
            state = NotificationState {},
        )
    }
