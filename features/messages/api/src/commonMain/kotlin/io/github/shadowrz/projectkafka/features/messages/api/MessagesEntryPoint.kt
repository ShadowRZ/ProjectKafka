package io.github.shadowrz.projectkafka.features.messages.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Parameters
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID

interface MessagesEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {}

    data class Params(val chatID: ChatID) : Parameters

    fun build(
        parent: Component,
        context: ComponentContext,
        chatID: ChatID,
        callback: Callback,
    ): Component
}
