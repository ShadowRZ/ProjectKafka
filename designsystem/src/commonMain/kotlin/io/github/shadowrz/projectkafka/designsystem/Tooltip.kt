package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
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
        tooltip = { TooltipScopeImpl(this).tooltip() },
        state = state.inner,
        onDismissRequest = onDismissRequest,
        focusable = focusable,
        enableUserInput = enableUserInput,
        hasAction = hasAction,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
data class TooltipState internal constructor(
    internal val inner: androidx.compose.material3.TooltipState,
)

/**
 * Create and remember the default [TooltipState] for [TooltipBox].
 *
 * @param initialIsVisible the initial value for the tooltip's visibility when drawn.
 * @param isPersistent [Boolean] that determines if the tooltip associated with this will be
 *   persistent or not. If isPersistent is true, then the tooltip will only be dismissed when the
 *   user clicks outside the bounds of the tooltip or if [TooltipState.dismiss] is called. When
 *   isPersistent is false, the tooltip will dismiss after a short duration. Ideally, this should be
 *   set to true when there is actionable content being displayed within a tooltip.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberTooltipState(
    initialIsVisible: Boolean = false,
    isPersistent: Boolean = false,
): TooltipState {
    val inner = androidx.compose.material3.rememberTooltipState(
        initialIsVisible = initialIsVisible,
        isPersistent = isPersistent,
    )

    return TooltipState(inner)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipScope.PlainTooltip(
    text: String,
    modifier: Modifier = Modifier,
) {
    this.inner.PlainTooltip(modifier = modifier) {
        Text(text)
    }
}

/**
 * [PopupPositionProvider] that should be used with either [RichTooltip] or [PlainTooltip]. It
 * correctly positions the tooltip in respect to the anchor content.
 *
 * @param positioning [TooltipAnchorPosition] that determines where the tooltip is placed
 *   relative to the anchor.
 * @param spacingBetweenTooltipAndAnchor the spacing between the tooltip and the anchor content.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberTooltipPositionProvider(
    positioning: TooltipAnchorPosition,
    spacingBetweenTooltipAndAnchor: Dp = 4.dp,
): PopupPositionProvider =
    androidx.compose.material3.TooltipDefaults.rememberTooltipPositionProvider(
        positioning = positioning.toMaterialPosition(),
        spacingBetweenTooltipAndAnchor = spacingBetweenTooltipAndAnchor,
    )

enum class TooltipAnchorPosition {
    Above,
    Below,
    Left,
    Right,
    Start,
    End,
}

abstract class TooltipScope {
    @OptIn(ExperimentalMaterial3Api::class)
    internal abstract val inner: androidx.compose.material3.TooltipScope

    /**
     * Used to obtain the [LayoutCoordinates] of the anchor content. This can be used to help draw
     * the caret pointing to the anchor content.
     */
    abstract fun MeasureScope.obtainAnchorBounds(): LayoutCoordinates?

    /**
     * Used to obtain the [PopupPositionProvider] used. This can be used to help draw the caret
     * pointing to the anchor content.
     */
    abstract fun obtainPositionProvider(): PopupPositionProvider
}

@OptIn(ExperimentalMaterial3Api::class)
internal class TooltipScopeImpl(
    override val inner: androidx.compose.material3.TooltipScope,
) : TooltipScope() {
    override fun MeasureScope.obtainAnchorBounds(): LayoutCoordinates? =
        inner.run {
            obtainAnchorBounds()
        }

    override fun obtainPositionProvider(): PopupPositionProvider = inner.obtainPositionProvider()
}

@OptIn(ExperimentalMaterial3Api::class)
private fun TooltipAnchorPosition.toMaterialPosition(): androidx.compose.material3.TooltipAnchorPosition =
    when (this) {
        TooltipAnchorPosition.Above -> androidx.compose.material3.TooltipAnchorPosition.Above
        TooltipAnchorPosition.Below -> androidx.compose.material3.TooltipAnchorPosition.Below
        TooltipAnchorPosition.Left -> androidx.compose.material3.TooltipAnchorPosition.Left
        TooltipAnchorPosition.Right -> androidx.compose.material3.TooltipAnchorPosition.Right
        TooltipAnchorPosition.Start -> androidx.compose.material3.TooltipAnchorPosition.Start
        TooltipAnchorPosition.End -> androidx.compose.material3.TooltipAnchorPosition.End
    }
