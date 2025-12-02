package io.github.shadowrz.projectkafka.features.preferences.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
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
