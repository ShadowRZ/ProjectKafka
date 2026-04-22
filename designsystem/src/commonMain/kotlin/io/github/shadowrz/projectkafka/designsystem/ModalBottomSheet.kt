package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

/**
 * Custom ModalBottomSheet that:
 * 1. Workarounds [https://issuetracker.google.com/issues/283843380](https://issuetracker.google.com/issues/283843380)
 * 2. If we haven't provided a custom drag handle, use the default drag handle without ripple.
 *
 * @see androidx.compose.material3.ModalBottomSheet
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = if (isSystemInDarkTheme()) BottomSheetDefaults.Elevation else 0.dp,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.modalWindowInsets },
    content: @Composable ColumnScope.() -> Unit,
) {
    val safeSheetState =
        if (LocalInspectionMode.current) {
            previewSheetState()
        } else {
            sheetState
        }

    val rippleConfiguration = LocalRippleConfiguration.current

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        androidx.compose.material3.ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            sheetState = safeSheetState,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            scrimColor = scrimColor,
            dragHandle = dragHandle,
            contentWindowInsets = contentWindowInsets,
        ) {
            CompositionLocalProvider(
                LocalRippleConfiguration provides rippleConfiguration,
            ) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun previewSheetState() =
    SheetState(
        skipPartiallyExpanded = true,
        initialValue = SheetValue.Expanded,
        positionalThreshold = { 1f },
        velocityThreshold = { 1f },
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
internal fun PreviewModalBottomSheet() =
    KafkaPreview {
        Box(modifier = Modifier.fillMaxSize()) {
            ModalBottomSheet(
                onDismissRequest = {},
            ) {
                Text(
                    "Sheet Content",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp).background(
                        color = Color.Magenta,
                    ),
                )
            }
        }
    }
