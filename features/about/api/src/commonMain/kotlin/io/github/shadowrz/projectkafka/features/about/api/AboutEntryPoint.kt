package io.github.shadowrz.projectkafka.features.about.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin

interface AboutEntryPoint {
    interface Callback : Plugin {
        fun onLicenses()
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        callback: Callback,
    ): Component
}
