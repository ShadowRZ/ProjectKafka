package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.runtime.Composable

@Composable
expect fun LockOrientation(orientation: ScreenOrientation)

enum class ScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
}
