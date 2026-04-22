package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.ToggleFloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.material3.FloatingActionButtonMenuScope as MaterialFloatingActionButtonMenuScope
import androidx.compose.material3.ToggleFloatingActionButtonScope as MaterialToggleFloatingActionButtonScope

/**
 * @see androidx.compose.material3.FloatingActionButtonMenu
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FloatingActionButtonMenu(
    expanded: Boolean,
    button: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.End,
    content: @Composable FloatingActionButtonMenuScope.() -> Unit,
) = androidx.compose.material3.FloatingActionButtonMenu(
    expanded = expanded,
    button = button,
    modifier = modifier,
    horizontalAlignment = horizontalAlignment,
    content = {
        FloatingActionButtonMenuScope(this).content()
    },
)

/**
 * Wrapper class to hide the Material3 [TopAppBarScrollBehavior][MaterialFloatingActionButtonMenuScope] implementation detail.
 *
 * @see androidx.compose.material3.FloatingActionButtonMenuScope
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
class FloatingActionButtonMenuScope internal constructor(
    internal val scope: MaterialFloatingActionButtonMenuScope,
) {
    val horizontalAlignment: Alignment.Horizontal by scope::horizontalAlignment
}

/**
 * Wrapper class to hide the Material3 [TopAppBarScrollBehavior][MaterialFloatingActionButtonMenuScope] implementation detail.
 *
 * @see androidx.compose.material3.FloatingActionButtonMenuScope
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
class ToggleFloatingActionButtonScope internal constructor(
    internal val scope: MaterialToggleFloatingActionButtonScope,
) {
    val checkedProgress: Float by scope::checkedProgress
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FloatingActionButtonMenuScope.FloatingActionButtonMenuItem(
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = KafkaTheme.materialColors.primaryContainer,
    contentColor: Color = KafkaTheme.materialColors.onPrimaryContainer,
) = this.scope.FloatingActionButtonMenuItem(
    modifier = modifier,
    onClick = onClick,
    text = text,
    icon = icon,
    containerColor = containerColor,
    contentColor = contentColor,
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ToggleFloatingActionButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: (Float) -> Color = ToggleFloatingActionButtonDefaults.containerColor(),
    contentAlignment: Alignment = Alignment.TopEnd,
    containerSize: (Float) -> Dp = ToggleFloatingActionButtonDefaults.containerSize(),
    containerCornerRadius: (Float) -> Dp =
        ToggleFloatingActionButtonDefaults.containerCornerRadius(),
    content: @Composable ToggleFloatingActionButtonScope.() -> Unit,
) = androidx.compose.material3.ToggleFloatingActionButton(
    modifier = modifier,
    checked = checked,
    onCheckedChange = onCheckedChange,
    containerColor = containerColor,
    contentAlignment = contentAlignment,
    containerSize = containerSize,
    containerCornerRadius = containerCornerRadius,
    content = {
        ToggleFloatingActionButtonScope(this).content()
    },
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
object ToggleFloatingActionButtonDefaults {
    @Composable
    internal fun iconColor(
        initialColor: Color = KafkaTheme.materialColors.onPrimaryContainer,
        finalColor: Color = KafkaTheme.materialColors.onPrimary,
    ): (Float) -> Color = { progress -> lerp(initialColor, finalColor, progress) }

    internal fun iconSize(
        initialSize: Dp = 24.0.dp,
        finalSize: Dp = 20.0.dp,
    ): (Float) -> Dp =
        { progress ->
            lerp(initialSize, finalSize, progress)
        }

    @Composable
    fun Modifier.animateIcon(
        checkedProgress: () -> Float,
        color: (Float) -> Color = iconColor(),
        size: (Float) -> Dp = iconSize(),
    ): Modifier =
        this
            .layout { measurable, _ ->
                val sizePx = size(checkedProgress()).roundToPx()
                val placeable = measurable.measure(Constraints.fixed(sizePx, sizePx))
                layout(sizePx, sizePx) { placeable.place(0, 0) }
            }.drawWithCache {
                val layer = obtainGraphicsLayer()
                layer.apply {
                    record { drawContent() }
                    this.colorFilter = ColorFilter.tint(color(checkedProgress()))
                }

                onDrawWithContent { drawLayer(graphicsLayer = layer) }
            }
}
