package io.github.shadowrz.projectkafka.libraries.components.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

fun PaddingValues.copy(
    layoutDirection: LayoutDirection,
    start: Dp? = null,
    top: Dp? = null,
    end: Dp? = null,
    bottom: Dp? = null,
) = PaddingValues(
    start = start ?: this.calculateStartPadding(layoutDirection),
    end = end ?: this.calculateEndPadding(layoutDirection),
    top = top ?: this.calculateTopPadding(),
    bottom = bottom ?: this.calculateBottomPadding(),
)
