package io.github.shadowrz.projectkafka.libraries.architecture

import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin

fun interface ReadyCallback : Plugin {
    fun ready()
}
