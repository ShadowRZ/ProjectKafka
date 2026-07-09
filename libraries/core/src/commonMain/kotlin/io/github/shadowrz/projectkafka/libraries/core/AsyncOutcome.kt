package io.github.shadowrz.projectkafka.libraries.core

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

@Stable
sealed interface AsyncOutcome<out T> : HanekokoroState {
    data object Loading : AsyncOutcome<Nothing>

    data class Success<T>(val value: T) : AsyncOutcome<T>
}
