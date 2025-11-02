package io.github.shadowrz.projectkafka.features.editmember.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint

interface AddMemberEntryPoint : FeatureEntryPoint {
    fun build(
        parent: Component,
        context: ComponentContext,
    ): Component
}
