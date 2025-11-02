package io.github.shadowrz.projectkafka.libraries.architecture

import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent

expect inline fun <reified T : Any> GenericComponent<*>.bindings(): T
