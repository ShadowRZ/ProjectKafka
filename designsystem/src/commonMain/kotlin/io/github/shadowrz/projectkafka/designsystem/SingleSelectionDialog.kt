package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_cancel
import io.github.shadowrz.projectkafka.libraries.strings.common_ok
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T> SingleSelectionDialog(
    options: List<T>,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit,
    itemTitle: (T) -> String,
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    icon: @Composable (() -> Unit)? = null,
    itemSubtitle: (T) -> String? = { null },
    confirmLabel: String = stringResource(CommonStrings.common_ok),
    dismissLabel: String = stringResource(CommonStrings.common_cancel),
    initialSelection: Int? = null,
) {
    var selection by rememberSaveable { mutableStateOf(initialSelection) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                text = confirmLabel,
                enabled = selection != null,
                onClick = { onConfirm(selection!!) },
            )
        },
        dismissButton = {
            TextButton(
                text = dismissLabel,
                onClick = onDismiss,
            )
        },
        title = title?.let { { Text(it) } },
        subtitle = subtitle?.let { { Text(it) } },
        icon = icon,
    ) {
        LazyColumn {
            itemsIndexed(options) { index, option ->
                RadioButtonListItem(
                    selected = index == selection,
                    onClick = { selection = index },
                    headlineContent = {
                        Text(itemTitle(option))
                    },
                    supportingContent =
                        itemSubtitle(option)?.let {
                            { Text(it) }
                        },
                )
            }
        }
    }
}

@Composable
fun <T> SingleSelectionDialog(
    options: List<T>,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    icon: @Composable (() -> Unit)? = null,
    confirmLabel: String = stringResource(CommonStrings.common_ok),
    dismissLabel: String = stringResource(CommonStrings.common_cancel),
    initialSelection: Int? = null,
    itemContent:
        @Composable
        (
            option: T,
            interactionSource: MutableInteractionSource,
            modifier: Modifier,
            selected: Boolean,
            onClick: () -> Unit,
        ) -> Unit,
) {
    var selection by rememberSaveable { mutableStateOf(initialSelection) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                text = confirmLabel,
                enabled = selection != null,
                onClick = { onConfirm(selection!!) },
            )
        },
        dismissButton = {
            TextButton(
                text = dismissLabel,
                onClick = onDismiss,
            )
        },
        title = title?.let { { Text(it) } },
        subtitle = subtitle?.let { { Text(it) } },
        icon = icon,
    ) {
        LazyColumn {
            itemsIndexed(options) { index, option ->
                val interactionSource = remember { MutableInteractionSource() }
                val selected = index == selection

                fun onClick() {
                    selection = index
                }

                itemContent(
                    option,
                    interactionSource,
                    Modifier.selectable(
                        selected = selected,
                        enabled = true,
                        interactionSource = interactionSource,
                        indication = LocalIndication.current,
                        onClick = ::onClick,
                    ),
                    selected,
                    ::onClick,
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
internal fun PreviewSingleSelectionDialog() = KafkaPreview {
    SingleSelectionDialog(
        options = listOf("A", "B", "C"),
        itemTitle = { it },
        onConfirm = {},
        onDismiss = {},
    )
}
