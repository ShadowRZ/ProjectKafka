package io.github.shadowrz.projectkafka.features.home.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID

interface HomeEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onAbout()

        fun onAddMember()

        fun onEditMember(memberID: MemberID)

        fun onDataManage()

        fun onSwitchSystem()

        fun onSettings()
    }

    interface Actions {
        fun dismissMemberPane(onComplete: () -> Unit)
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        callback: Callback,
    ): Component
}
