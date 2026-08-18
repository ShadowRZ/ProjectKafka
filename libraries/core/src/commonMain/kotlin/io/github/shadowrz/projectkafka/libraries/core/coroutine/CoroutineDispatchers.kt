package io.github.shadowrz.projectkafka.libraries.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class CoroutineDispatchers(
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher,
) {
    @Suppress("detekt:InjectDispatcher")
    companion object {
        val Default =
            CoroutineDispatchers(
                io = Dispatchers.IO,
                computation = Dispatchers.Default,
                main = Dispatchers.Main,
            )
    }
}
