package io.github.shadowrz.projectkafka.libraries.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.plus

fun CoroutineScope.childScope(
    name: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
) = run {
    val supervisorJob = SupervisorJob(parent = coroutineContext.job)

    this + dispatcher + supervisorJob + CoroutineName(name)
}
