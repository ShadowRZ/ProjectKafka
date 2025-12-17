package io.github.shadowrz.projectkafka.features.switchsystem.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

interface SwitchSystemEntryPoint {
    interface Callback : Plugin {
        fun onCreateSystem()

        fun onSwitchSystem(id: SystemID)
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        callback: Callback,
    ): Component
}
