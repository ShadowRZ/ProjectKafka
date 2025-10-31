package io.github.shadowrz.projectkafka.libraries.architecture

expect inline fun <reified T : Any> GenericComponent<*>.bindings(): T
