package io.github.shadowrz.projectkafka.libraries.architecture

import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.PluginsOwner
import io.github.shadowrz.hanekokoro.framework.runtime.plugins

interface Parameters : Plugin

inline fun <reified P : Parameters> PluginsOwner.paramters() =
    requireNotNull(plugins<P>().firstOrNull()) {
        "Please provide parameters of type ${P::class.qualifiedName} to component ${this::class.qualifiedName}."
    }
