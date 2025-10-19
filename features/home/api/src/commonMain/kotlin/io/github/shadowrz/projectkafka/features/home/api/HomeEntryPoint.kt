package io.github.shadowrz.projectkafka.features.home.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin

interface HomeEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onAbout()

        fun onAddMember()
    }

    interface Actions {
        suspend fun navigateToSwitchLog()
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        callback: Callback,
    ): Component
}
