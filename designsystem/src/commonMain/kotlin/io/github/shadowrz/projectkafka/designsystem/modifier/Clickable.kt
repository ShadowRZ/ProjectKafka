package io.github.shadowrz.projectkafka.designsystem.modifier

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

fun Modifier.maybeClickable(onClick: (() -> Unit)? = null): Modifier =
    this then if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
