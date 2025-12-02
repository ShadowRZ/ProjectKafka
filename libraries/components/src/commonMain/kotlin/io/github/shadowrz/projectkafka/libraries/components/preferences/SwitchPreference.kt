package io.github.shadowrz.projectkafka.libraries.components.preferences

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.shadowrz.projectkafka.libraries.components.preview.PreviewGroups
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Add
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        modifier =
            modifier.clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
            ) {
                onCheckedChange(checked)
            },
    )
}

@Preview(group = PreviewGroups.Preference)
@Composable
internal fun SwitchPreferencePreview() =
    ProjectKafkaPreview {
        var checked by remember { mutableStateOf(false) }
        SwitchPreference(
            headlineContent = { Text("Switch") },
            supportingContent = { Text("This is an example") },
            leadingContent = { Icon(MaterialIcons.Add, null) },
            checked = checked,
            onCheckedChange = { checked = it },
        )
    }

@Preview(group = PreviewGroups.Preference)
@Composable
internal fun DisabledSwitchPreferencePreview() =
    ProjectKafkaPreview {
        SwitchPreference(
            headlineContent = { Text("Switch") },
            supportingContent = { Text("This is an example") },
            leadingContent = { Icon(MaterialIcons.Add, null) },
            checked = false,
            enabled = false,
            onCheckedChange = {},
        )
    }
