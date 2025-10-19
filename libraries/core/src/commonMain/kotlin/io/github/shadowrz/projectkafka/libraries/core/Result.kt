package io.github.shadowrz.projectkafka.libraries.core

import androidx.compose.runtime.Stable

@Stable
sealed interface Result<out T> {
    data object Loading : Result<Nothing>

    data class Success<T>(
        val value: T,
    ) : Result<T>
}
