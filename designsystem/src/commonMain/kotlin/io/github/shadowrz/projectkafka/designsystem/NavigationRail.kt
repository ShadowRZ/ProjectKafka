package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun NavigationRail(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationRailDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    header: @Composable (ColumnScope.() -> Unit)? = null,
    windowInsets: WindowInsets = NavigationRailDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit,
) {
    androidx.compose.material3.NavigationRail(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        header = header,
        windowInsets = windowInsets,
        content = content,
    )
}

@Composable
fun NavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationRailItemColors = NavigationRailItemDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    androidx.compose.material3.NavigationRailItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = icon,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = colors,
        interactionSource = interactionSource,
    )
}
