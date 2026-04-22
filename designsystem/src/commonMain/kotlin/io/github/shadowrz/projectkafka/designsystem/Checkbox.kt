package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun Checkbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    androidx.compose.material3.Checkbox(
        modifier = modifier.minimumInteractiveComponentSize(),
        checked = checked,
        enabled = enabled,
        onCheckedChange = onCheckedChange,
        interactionSource = interactionSource,
    )
}

@Composable
@PreviewLightDark
@PreviewDynamicColors
internal fun PreviewCheckbox() =
    KafkaPreview {
        Row {
            Column {
                Checkbox(checked = true)
                Checkbox(checked = false)
            }
            Column {
                Checkbox(enabled = false, checked = true)
                Checkbox(enabled = false, checked = false)
            }
        }
    }
