package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.icons.InfoOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
@NonRestartableComposable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    progress: Boolean = false,
    destructive: Boolean = false,
    leadingIcon: ImageVector? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    ButtonInternal(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        progress = progress,
        leadingIcon = leadingIcon,
        shape = ButtonDefaults.shape,
        colors = if (destructive) destructiveButtonColors() else ButtonDefaults.buttonColors(),
        interactionSource = interactionSource,
    )
}

@Composable
@NonRestartableComposable
fun FilledTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    progress: Boolean = false,
    leadingIcon: ImageVector? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    ButtonInternal(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        progress = progress,
        leadingIcon = leadingIcon,
        shape = ButtonDefaults.filledTonalShape,
        colors = ButtonDefaults.filledTonalButtonColors(),
        elevation = ButtonDefaults.filledTonalButtonElevation(),
        interactionSource = interactionSource,
    )
}

@Composable
@NonRestartableComposable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    progress: Boolean = false,
    destructive: Boolean = false,
    leadingIcon: ImageVector? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    ButtonInternal(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        progress = progress,
        leadingIcon = leadingIcon,
        shape = ButtonDefaults.outlinedShape,
        colors = if (destructive) destructiveOutlinedButtonColors() else ButtonDefaults.outlinedButtonColors(),
        elevation = null,
        border = ButtonDefaults
            .outlinedButtonBorder(
                enabled,
            ).run {
                val brush = this.brush
                val alpha = if (enabled) 1f else 0.1f
                copy(brush = if (destructive) SolidColor(MaterialTheme.colorScheme.error.copy(alpha = alpha)) else brush)
            },
        interactionSource = interactionSource,
    )
}

@Composable
@NonRestartableComposable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    progress: Boolean = false,
    destructive: Boolean = false,
    leadingIcon: ImageVector? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    ButtonInternal(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        progress = progress,
        leadingIcon = leadingIcon,
        shape = ButtonDefaults.textShape,
        colors = if (destructive) destructiveTextButtonColors() else ButtonDefaults.textButtonColors(),
        contentPadding = ButtonDefaults.TextButtonContentPadding,
        interactionSource = interactionSource,
    )
}

@Composable
private fun ButtonInternal(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    progress: Boolean = false,
    leadingIcon: ImageVector? = null,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        when {
            progress -> {
                // 20dp CircularProgressIndicator / 2dp strokeWidth is nonstandard
                CircularProgressIndicator(
                    // Can't use ButtonDefaults.IconSize since that's incorrect
                    // See https://m3.material.io/components/buttons/specs#a69008b5-4efe-43ec-9d23-cbece83b08e6
                    modifier = Modifier.progressSemantics().size(20.dp),
                    color = LocalContentColor.current,
                    strokeWidth = 2.dp,
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            }

            leadingIcon != null -> {
                Icon(
                    leadingIcon,
                    contentDescription = null,
                    tint = LocalContentColor.current,
                    // Can't use ButtonDefaults.IconSize since that's incorrect
                    // See https://m3.material.io/components/buttons/specs#a69008b5-4efe-43ec-9d23-cbece83b08e6
                    modifier = Modifier.progressSemantics().size(20.dp),
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            }
        }
        Text(text)
    }
}

@Composable
private fun destructiveButtonColors() =
    ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError,
        disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
        disabledContentColor = MaterialTheme.colorScheme.onError.copy(alpha = 0.38f),
    )

@Composable
private fun destructiveOutlinedButtonColors() =
    ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.error,
        disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = 0.38f),
    )

@Composable
private fun destructiveTextButtonColors() =
    ButtonDefaults.textButtonColors(
        contentColor = MaterialTheme.colorScheme.error,
        disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = 0.38f),
    )

@Composable
@PreviewLightDark
@PreviewDynamicColors
internal fun PreviewButton() =
    KafkaPreview {
        Row {
            Column {
                Button("Hello World", onClick = {})
                Button("Hello World", onClick = {}, leadingIcon = KafkaIcons.InfoOutline)
                Button("Hello World", onClick = {}, leadingIcon = KafkaIcons.InfoOutline, progress = true)
                Button("Hello World", onClick = {}, destructive = true)
                Button("Hello World", onClick = {}, destructive = true, leadingIcon = KafkaIcons.InfoOutline)
                Button("Hello World", onClick = {}, destructive = true, leadingIcon = KafkaIcons.InfoOutline, progress = true)
            }
            Column {
                Button("Hello World", onClick = {}, enabled = false)
                Button("Hello World", onClick = {}, enabled = false, leadingIcon = KafkaIcons.InfoOutline)
                Button("Hello World", onClick = {}, enabled = false, leadingIcon = KafkaIcons.InfoOutline, progress = true)
                Button("Hello World", onClick = {}, enabled = false, destructive = true)
                Button("Hello World", onClick = {}, enabled = false, destructive = true, leadingIcon = KafkaIcons.InfoOutline)
                Button(
                    "Hello World",
                    onClick = {},
                    enabled = false,
                    destructive = true,
                    leadingIcon = KafkaIcons.InfoOutline,
                    progress = true,
                )
            }
        }
    }

@Composable
@PreviewLightDark
@PreviewDynamicColors
internal fun PreviewOutlinedButton() =
    KafkaPreview {
        Row {
            Column {
                OutlinedButton("Hello World", onClick = {})
                OutlinedButton("Hello World", onClick = {}, leadingIcon = KafkaIcons.InfoOutline)
                OutlinedButton("Hello World", onClick = {}, leadingIcon = KafkaIcons.InfoOutline, progress = true)
                OutlinedButton("Hello World", onClick = {}, destructive = true)
                OutlinedButton("Hello World", onClick = {}, destructive = true, leadingIcon = KafkaIcons.InfoOutline)
                OutlinedButton("Hello World", onClick = {}, destructive = true, leadingIcon = KafkaIcons.InfoOutline, progress = true)
            }
            Column {
                OutlinedButton("Hello World", onClick = {}, enabled = false)
                OutlinedButton("Hello World", onClick = {}, enabled = false, leadingIcon = KafkaIcons.InfoOutline)
                OutlinedButton("Hello World", onClick = {}, enabled = false, leadingIcon = KafkaIcons.InfoOutline, progress = true)
                OutlinedButton("Hello World", onClick = {}, enabled = false, destructive = true)
                OutlinedButton("Hello World", onClick = {}, enabled = false, destructive = true, leadingIcon = KafkaIcons.InfoOutline)
                OutlinedButton(
                    "Hello World",
                    onClick = {},
                    enabled = false,
                    destructive = true,
                    leadingIcon = KafkaIcons.InfoOutline,
                    progress = true,
                )
            }
        }
    }

@Composable
@PreviewLightDark
@PreviewDynamicColors
internal fun PreviewTextButton() =
    KafkaPreview {
        Row {
            Column {
                TextButton("Hello World", onClick = {})
                TextButton("Hello World", onClick = {}, leadingIcon = KafkaIcons.InfoOutline)
                TextButton("Hello World", onClick = {}, leadingIcon = KafkaIcons.InfoOutline, progress = true)
                TextButton("Hello World", onClick = {}, destructive = true)
                TextButton("Hello World", onClick = {}, destructive = true, leadingIcon = KafkaIcons.InfoOutline)
                TextButton("Hello World", onClick = {}, destructive = true, leadingIcon = KafkaIcons.InfoOutline, progress = true)
            }
            Column {
                TextButton("Hello World", onClick = {}, enabled = false)
                TextButton("Hello World", onClick = {}, enabled = false, leadingIcon = KafkaIcons.InfoOutline)
                TextButton("Hello World", onClick = {}, enabled = false, leadingIcon = KafkaIcons.InfoOutline, progress = true)
                TextButton("Hello World", onClick = {}, enabled = false, destructive = true)
                TextButton("Hello World", onClick = {}, enabled = false, destructive = true, leadingIcon = KafkaIcons.InfoOutline)
                TextButton(
                    "Hello World",
                    onClick = {},
                    enabled = false,
                    destructive = true,
                    leadingIcon = KafkaIcons.InfoOutline,
                    progress = true,
                )
            }
        }
    }

@Composable
@PreviewLightDark
@PreviewDynamicColors
internal fun PreviewFilledTonalButton() =
    KafkaPreview {
        Row {
            Column {
                FilledTonalButton("Hello World", onClick = {})
                FilledTonalButton("Hello World", onClick = {}, leadingIcon = KafkaIcons.InfoOutline)
                FilledTonalButton("Hello World", onClick = {}, leadingIcon = KafkaIcons.InfoOutline, progress = true)
            }
            Column {
                FilledTonalButton("Hello World", onClick = {}, enabled = false)
                FilledTonalButton("Hello World", onClick = {}, enabled = false, leadingIcon = KafkaIcons.InfoOutline)
                FilledTonalButton("Hello World", onClick = {}, enabled = false, leadingIcon = KafkaIcons.InfoOutline, progress = true)
            }
        }
    }
