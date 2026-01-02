package io.github.shadowrz.projectkafka.libraries.systemgraph

import io.github.shadowrz.projectkafka.libraries.data.api.System

fun interface SystemGraphFactory {
    fun create(system: System): Any
}
