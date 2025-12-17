package io.github.shadowrz.projectkafka.libraries.core

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

@Stable
sealed interface Result<out T> : HanekokoroState {
    data object Loading : Result<Nothing>

    data class Success<T>(
        val value: T,
    ) : Result<T>
}
