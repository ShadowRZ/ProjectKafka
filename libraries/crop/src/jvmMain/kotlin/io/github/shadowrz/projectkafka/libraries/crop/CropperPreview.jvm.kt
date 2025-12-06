package io.github.shadowrz.projectkafka.libraries.crop

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates

internal actual fun Modifier.disabledSystemGestureArea(exclusion: (LayoutCoordinates) -> Rect): Modifier = this
