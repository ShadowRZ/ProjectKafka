package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.runtime.Composable

@Composable
expect fun LockOrientation(orientation: ScreenOrientation)

enum class ScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
}
