package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.icons.InfoOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
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
    Button(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        progress = progress,
        destructive = destructive,
        leadingIcon = leadingIcon,
        variant = ButtonVariant.Filled,
        interactionSource = interactionSource,
    )
}

@Composable
fun FilledTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    progress: Boolean = false,
    leadingIcon: ImageVector? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    Button(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        progress = progress,
        leadingIcon = leadingIcon,
        variant = ButtonVariant.FilledTonal,
        interactionSource = interactionSource,
    )
}

@Composable
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
    Button(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        progress = progress,
        destructive = destructive,
        leadingIcon = leadingIcon,
        variant = ButtonVariant.Outlined,
        interactionSource = interactionSource,
    )
}

@Composable
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
    Button(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        progress = progress,
        destructive = destructive,
        leadingIcon = leadingIcon,
        variant = ButtonVariant.Text,
        interactionSource = interactionSource,
    )
}

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    variant: ButtonVariant,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    progress: Boolean = false,
    destructive: Boolean = false,
    leadingIcon: ImageVector? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = variant.shape(),
        colors = variant.colors(destructive = destructive),
        elevation = variant.elevation(),
        border = variant.border(enabled = enabled, destructive = destructive),
        contentPadding = variant.contentPadding(),
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
