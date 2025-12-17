package io.github.shadowrz.projectkafka.features.createsystem.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

interface CreateSystemEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onFinished(id: SystemID)
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        callback: Callback,
    ): Component
}
