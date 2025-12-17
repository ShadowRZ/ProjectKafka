package io.github.shadowrz.projectkafka.features.preferences.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint

interface PreferencesEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onDataManage()
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        callback: Callback,
    ): Component
}
