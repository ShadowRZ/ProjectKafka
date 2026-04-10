package io.github.shadowrz.projectkafka.features.quickstart.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint

interface QuickStartEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onCreateSystem()

        fun onDataManage()
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        callback: Callback,
    ): Component
}
