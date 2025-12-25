package io.github.shadowrz.projectkafka.libraries.core

inline fun <R, T> Result<T>.map(transform: (T) -> R): Result<R> =
    when (this) {
        is Result.Success<T> -> Result.Success(transform(value))
        Result.Loading -> Result.Loading
    }
