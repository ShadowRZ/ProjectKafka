package io.github.shadowrz.projectkafka.designsystem.internal.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
internal interface SnackbarContent {
    @Composable
    fun asString(): String
}
