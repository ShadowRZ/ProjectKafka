package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

@Composable
fun MobileLockOrientation(orientation: ScreenOrientation) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()

    if (
        !windowAdaptiveInfo.windowSizeClass.isAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND,
            WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND,
        )
    ) {
        LockOrientation(orientation = orientation)
    }
}
