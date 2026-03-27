package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.TooltipScope
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupPositionProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
fun TooltipBox(
    positionProvider: PopupPositionProvider,
    tooltip: @Composable TooltipScope.() -> Unit,
    state: TooltipState,
    modifier: Modifier = Modifier,
    onDismissRequest: (() -> Unit)? = null,
    focusable: Boolean = false,
    enableUserInput: Boolean = true,
    hasAction: Boolean = false,
    content: @Composable () -> Unit,
) {
    androidx.compose.material3.TooltipBox(
        modifier = modifier,
        positionProvider = positionProvider,
        tooltip = tooltip,
        state = state,
        onDismissRequest = onDismissRequest,
        focusable = focusable,
        enableUserInput = enableUserInput,
        hasAction = hasAction,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
fun TooltipScope.PlainTooltip(
    text: String,
    modifier: Modifier = Modifier,
) {
    PlainTooltip(modifier = modifier) {
        Text(text)
    }
}
