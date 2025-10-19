package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

@Composable
inline fun HiddenInTwoPane(content: @Composable () -> Unit) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    if (!windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)) {
        content()
    }
}
