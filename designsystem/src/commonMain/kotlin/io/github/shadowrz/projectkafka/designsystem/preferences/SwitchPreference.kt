package io.github.shadowrz.projectkafka.designsystem.preferences

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.Switch
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.Add
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun SwitchPreference(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    ListItem(
        headlineContent = headlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                interactionSource = interactionSource,
            )
        },
        enabled = enabled,
        modifier =
            modifier.toggleable(
                value = checked,
                enabled = enabled,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onValueChange = onCheckedChange,
            ),
    )
}

@Composable
@PreviewLightDark
internal fun PreviewSwitchPreference() =
    KafkaPreview {
        Column {
            var checked by remember { mutableStateOf(false) }
            SwitchPreference(
                headlineContent = { Text("Switch") },
                supportingContent = { Text("This is an example") },
                leadingContent = { Icon(KafkaIcons.Add, null) },
                checked = checked,
                onCheckedChange = { checked = it },
            )
            SwitchPreference(
                headlineContent = { Text("Switch") },
                supportingContent = { Text("This is an example") },
                leadingContent = { Icon(KafkaIcons.Add, null) },
                checked = false,
                enabled = false,
                onCheckedChange = {},
            )
        }
    }
