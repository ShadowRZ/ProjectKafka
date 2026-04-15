package io.github.shadowrz.projectkafka.designsystem.internal.snackbar

import androidx.compose.material3.SnackbarDuration
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
internal data class SnackbarData(
    val message: SnackbarContent,
    val actionLabel: SnackbarContent?,
    val withDismissAction: Boolean,
    val duration: SnackbarDuration,
    val shown: AtomicBoolean,
)
