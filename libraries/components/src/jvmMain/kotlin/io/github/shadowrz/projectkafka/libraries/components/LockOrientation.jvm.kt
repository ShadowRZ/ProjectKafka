package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.runtime.Composable

@Suppress("unused")
@Composable
actual fun LockOrientation(orientation: ScreenOrientation) {
    // JVM platform have no need to lock orientation.
}
