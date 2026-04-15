package io.github.shadowrz.projectkafka.designsystem.adaptive

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable

@Composable
fun <T> adaptiveValue(
    compact: T,
    medium: T = compact,
    expanded: T = medium,
): T {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val width = windowAdaptiveInfo.windowSizeClass.minWidthDp

    return when {
        width >= WINDOW_SIZE_CLASS_EXPANDED_LOWER_BOUND -> expanded
        width >= WINDOW_SIZE_CLASS_MEDIUM_LOWER_BOUND -> medium
        else -> compact
    }
}
