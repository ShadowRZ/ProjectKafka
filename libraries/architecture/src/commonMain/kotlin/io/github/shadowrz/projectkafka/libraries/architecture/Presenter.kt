package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.compose.runtime.Composable

fun interface Presenter<S> {
    @Composable
    fun present(): S
}
