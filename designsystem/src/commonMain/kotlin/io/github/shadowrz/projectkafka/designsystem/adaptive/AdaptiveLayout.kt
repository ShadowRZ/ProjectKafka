package io.github.shadowrz.projectkafka.designsystem.adaptive

import androidx.compose.runtime.Composable

object AdaptiveLayout {
    @Composable
    fun useNavigationRail() =
        adaptiveValue(
            compact = false,
            medium = true,
        )
}
