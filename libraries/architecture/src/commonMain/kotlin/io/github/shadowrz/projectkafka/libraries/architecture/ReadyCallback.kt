package io.github.shadowrz.projectkafka.libraries.architecture

import io.github.shadowrz.hanekokoro.framework.runtime.Plugin

fun interface ReadyCallback : Plugin {
    fun ready()
}
