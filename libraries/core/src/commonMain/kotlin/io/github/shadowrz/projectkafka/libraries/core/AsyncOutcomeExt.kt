package io.github.shadowrz.projectkafka.libraries.core

inline fun <R, T> AsyncOutcome<T>.map(transform: (T) -> R): AsyncOutcome<R> =
    when (this) {
        is AsyncOutcome.Success<T> -> AsyncOutcome.Success(transform(value))
        AsyncOutcome.Loading -> AsyncOutcome.Loading
    }
