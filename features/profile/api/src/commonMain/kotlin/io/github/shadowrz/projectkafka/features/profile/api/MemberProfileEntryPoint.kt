package io.github.shadowrz.projectkafka.features.profile.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID

interface MemberProfileEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onEditMember()
    }

    data class Params(
        val memberID: MemberID,
    ) : Plugin

    fun build(
        parent: Component,
        context: ComponentContext,
        memberID: MemberID,
        callback: Callback,
    ): Component
}
