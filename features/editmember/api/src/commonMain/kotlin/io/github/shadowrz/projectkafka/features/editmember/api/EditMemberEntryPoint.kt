package io.github.shadowrz.projectkafka.features.editmember.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Parameters
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID

interface EditMemberEntryPoint : FeatureEntryPoint {
    data class Params(
        val memberID: MemberID,
    ) : Parameters

    interface Callback : Plugin {
        fun onDeleteMember()
    }

    fun build(
        parent: Component,
        context: ComponentContext,
        memberID: MemberID,
        callback: Callback,
    ): Component
}
