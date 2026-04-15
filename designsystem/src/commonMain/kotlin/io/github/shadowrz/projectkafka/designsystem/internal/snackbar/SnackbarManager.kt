package io.github.shadowrz.projectkafka.designsystem.internal.snackbar

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex

internal class SnackbarManager {
    val deque = ArrayDeque<SnackbarData>()
    val mutex = Mutex()
    val current: Flow<SnackbarData?> = flow {
        mutex.lock()
        emit(deque.firstOrNull())
    }

    fun queue(data: SnackbarData) {
        deque.add(data)
        if (mutex.isLocked) mutex.unlock()
    }

    fun pop() {
        deque.removeFirstOrNull()
        if (mutex.isLocked) mutex.unlock()
    }

    fun clear() {
        deque.clear()
        if (mutex.isLocked) mutex.unlock()
    }

    companion object
}
