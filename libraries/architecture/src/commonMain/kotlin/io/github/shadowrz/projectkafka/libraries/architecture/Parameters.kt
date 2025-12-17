package io.github.shadowrz.projectkafka.libraries.architecture

import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.PluginsOwner
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.maybePlugin

interface Parameters : Plugin

inline fun <reified P : Parameters> PluginsOwner.paramters() =
    requireNotNull(maybePlugin<P>()) {
        "Please provide parameters of type ${P::class.qualifiedName} to component ${this::class.qualifiedName}."
    }
