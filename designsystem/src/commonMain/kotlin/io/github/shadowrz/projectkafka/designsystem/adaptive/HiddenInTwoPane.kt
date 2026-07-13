package io.github.shadowrz.projectkafka.designsystem.adaptive

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

@Composable
inline fun HiddenInTwoPane(content: @Composable () -> Unit) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    if (!windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)) {
        content()
    }
}
