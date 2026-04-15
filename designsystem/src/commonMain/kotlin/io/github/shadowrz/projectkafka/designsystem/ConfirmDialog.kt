package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_cancel
import io.github.shadowrz.projectkafka.libraries.strings.common_ok
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConfirmDialog(
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    icon: @Composable (() -> Unit)? = null,
    confirmLabel: String = stringResource(CommonStrings.common_ok),
    dismissLabel: String = stringResource(CommonStrings.common_cancel),
    destructive: Boolean = false,
) = AlertDialog(
    modifier = modifier,
    onDismissRequest = onDismiss,
    confirmButton = {
        TextButton(
            text = confirmLabel,
            onClick = onConfirm,
            destructive = destructive,
        )
    },
    dismissButton = {
        TextButton(
            text = dismissLabel,
            onClick = onDismiss,
        )
    },
    title = {
        title?.let { Text(it) }
    },
    text = {
        Text(text)
    },
    icon = icon,
)

@Composable
@PreviewLightDark
internal fun PreviewConfirmDialog() =
    KafkaPreview {
        ConfirmDialog(
            text = "Proceed?",
            onConfirm = {},
            onDismiss = {},
        )
    }
