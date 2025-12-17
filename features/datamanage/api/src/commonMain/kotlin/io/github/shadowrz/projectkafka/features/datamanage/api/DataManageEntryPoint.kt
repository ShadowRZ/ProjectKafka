package io.github.shadowrz.projectkafka.features.datamanage.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint

interface DataManageEntryPoint : FeatureEntryPoint {
    fun build(
        parent: Component,
        context: ComponentContext,
    ): Component
}
