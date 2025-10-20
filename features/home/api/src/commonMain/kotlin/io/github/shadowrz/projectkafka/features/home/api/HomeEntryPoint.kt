package io.github.shadowrz.projectkafka.features.home.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID

interface HomeEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onAbout()

        fun onAddMember()

        fun onEditMember(memberID: MemberID)
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
