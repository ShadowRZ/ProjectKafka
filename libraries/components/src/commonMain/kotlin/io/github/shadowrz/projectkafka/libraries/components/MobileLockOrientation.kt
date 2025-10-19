package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

@Composable
fun MobileLockOrientation(orientation: ScreenOrientation) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    if (!windowAdaptiveInfo.windowSizeClass.isAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND,
            WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND,
        )
    ) {
        LockOrientation(orientation = orientation)
    }
}
