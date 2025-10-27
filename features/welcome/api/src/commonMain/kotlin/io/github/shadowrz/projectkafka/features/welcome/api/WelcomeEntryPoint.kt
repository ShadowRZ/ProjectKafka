package io.github.shadowrz.projectkafka.features.welcome.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin

interface WelcomeEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onCreateSystem()

        fun onLearnMore()

        fun onDataManage()
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        callback: Callback,
    ): Component
}
