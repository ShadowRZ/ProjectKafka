package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.takeOrElse
import androidx.compose.ui.window.DialogProperties
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(),
) = androidx.compose.material3.AlertDialog(
    modifier = modifier,
    onDismissRequest = onDismissRequest,
    confirmButton = confirmButton,
    dismissButton = dismissButton,
    icon = icon,
    title = title,
    text = text,
    shape = shape,
    containerColor = containerColor,
    iconContentColor = iconContentColor,
    titleContentColor = titleContentColor,
    textContentColor = textContentColor,
    tonalElevation = tonalElevation,
    properties = properties,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    subtitleContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit,
) = androidx.compose.material3.BasicAlertDialog(
    modifier = modifier,
    onDismissRequest = onDismissRequest,
    properties = properties,
) {
    AlertDialogContent(
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        subtitle = subtitle,
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        subtitleContentColor = subtitleContentColor,
        tonalElevation = tonalElevation,
        content = content,
    )
}

@Composable
@PreviewLightDark
internal fun PreviewAlertDialog() =
    KafkaPreview {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton("OK", onClick = {})
            },
        ) {
            Box(modifier = Modifier.background(Color.Gray).fillMaxWidth()) {
                Text("[Content]")
            }
        }
    }

/**
 * [FlowRow] for dialog buttons. The confirm button is expected to be the first child of [content].
 */
@Composable
internal fun AlertDialogFlowRow(
    mainAxisSpacing: Dp,
    crossAxisSpacing: Dp,
    content: @Composable () -> Unit,
) {
    val originalLayoutDirection = LocalLayoutDirection.current
    // The confirm button comes BEFORE the dismiss button when stacked vertically,
    // but AFTER the dismiss button when stacked horizontally.
    CompositionLocalProvider(LocalLayoutDirection provides originalLayoutDirection.flip()) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(mainAxisSpacing),
            verticalArrangement = Arrangement.spacedBy(crossAxisSpacing),
        ) {
            CompositionLocalProvider(
                LocalLayoutDirection provides originalLayoutDirection,
                content = content,
            )
        }
    }
}

@Composable
internal fun AlertDialogContent(
    icon: (@Composable () -> Unit)?,
    title: (@Composable () -> Unit)?,
    subtitle: (@Composable () -> Unit)?,
    shape: Shape,
    containerColor: Color,
    tonalElevation: Dp,
    iconContentColor: Color,
    titleContentColor: Color,
    subtitleContentColor: Color,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) = AlertDialogContent(
    modifier = modifier,
    icon = icon,
    title = title,
    subtitle = subtitle,
    shape = shape,
    containerColor = containerColor,
    tonalElevation = tonalElevation,
    iconContentColor = iconContentColor,
    titleContentColor = titleContentColor,
    subtitleContentColor = subtitleContentColor,
    buttons = {
        val buttonPaddingFromMICS =
            LocalMinimumInteractiveComponentSize.current.takeOrElse { 0.dp } -
                ButtonDefaults.MinHeight
        AlertDialogFlowRow(
            mainAxisSpacing = ButtonsMainAxisSpacing,
            crossAxisSpacing =
                (ButtonsCrossAxisSpacing - buttonPaddingFromMICS).coerceIn(
                    0.dp,
                    ButtonsCrossAxisSpacing,
                ),
        ) {
            confirmButton()
            dismissButton?.invoke()
        }
    },
    content = content,
)

@Composable
internal fun AlertDialogContent(
    buttons: @Composable () -> Unit,
    icon: (@Composable () -> Unit)?,
    title: (@Composable () -> Unit)?,
    subtitle: (@Composable () -> Unit)?,
    shape: Shape,
    containerColor: Color,
    tonalElevation: Dp,
    iconContentColor: Color,
    titleContentColor: Color,
    subtitleContentColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        tonalElevation = tonalElevation,
    ) {
        Column {
            Column(modifier = Modifier.padding(DialogTopPadding)) {
                icon?.let {
                    CompositionLocalProvider(LocalContentColor provides iconContentColor) {
                        Box(Modifier.padding(IconPadding).align(Alignment.CenterHorizontally)) {
                            icon()
                        }
                    }
                }
                title?.let {
                    ProvideContentColorTextStyle(
                        contentColor = titleContentColor,
                        textStyle = KafkaTheme.typography.headlineSmall,
                    ) {
                        Box(
                            // Align the title to the center when an icon is present.
                            Modifier
                                .padding(TitlePadding)
                                .align(
                                    if (icon == null) {
                                        Alignment.Start
                                    } else {
                                        Alignment.CenterHorizontally
                                    },
                                ),
                        ) {
                            title()
                        }
                    }
                }
                subtitle?.let {
                    val textStyle = KafkaTheme.typography.bodyMedium
                    ProvideContentColorTextStyle(
                        contentColor = subtitleContentColor,
                        textStyle = textStyle,
                    ) {
                        Box(
                            Modifier
                                .weight(weight = 1f, fill = false)
                                .padding(TextPadding)
                                .align(Alignment.Start),
                        ) {
                            subtitle()
                        }
                    }
                }
            }
            content()
            Box(modifier = Modifier.padding(DialogBottomPadding).align(Alignment.End)) {
                val textStyle = KafkaTheme.typography.labelLarge
                CompositionLocalProvider(
                    LocalTextStyle provides textStyle,
                    content = buttons,
                )
            }
        }
    }
}

private fun LayoutDirection.flip(): LayoutDirection =
    when (this) {
        LayoutDirection.Ltr -> LayoutDirection.Rtl
        LayoutDirection.Rtl -> LayoutDirection.Ltr
    }

/**
 * A convenience method to provide values to both [LocalContentColor] and [LocalTextStyle] in one
 * call. This is less expensive than nesting calls to [CompositionLocalProvider].
 *
 * Text styles will be merged with the current value of [LocalTextStyle].
 */
@Composable
internal fun ProvideContentColorTextStyle(
    contentColor: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) {
    val mergedStyle = LocalTextStyle.current.merge(textStyle)
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides mergedStyle,
        content = content,
    )
}

private val ButtonsMainAxisSpacing = 8.dp
private val ButtonsCrossAxisSpacing = 8.dp

// Paddings for each of the dialog's parts.
private val DialogTopPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 24.dp)
private val DialogBottomPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp)
private val IconPadding = PaddingValues(bottom = 16.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val TextPadding = PaddingValues(bottom = 24.dp)
