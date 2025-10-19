package io.github.shadowrz.projectkafka.libraries.components.extensions

import androidx.compose.ui.Modifier

fun Modifier.applyIf(
    condition: Boolean,
    whenTrue: Modifier.() -> Modifier,
) = this then if (condition) whenTrue() else Modifier
