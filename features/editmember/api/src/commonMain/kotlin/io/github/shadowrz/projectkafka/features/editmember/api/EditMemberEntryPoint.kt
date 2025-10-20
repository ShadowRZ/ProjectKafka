package io.github.shadowrz.projectkafka.features.editmember.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID

interface EditMemberEntryPoint : FeatureEntryPoint {
    data class Params(
        val memberID: MemberID,
    ) : Plugin

    fun build(
        parent: Component,
        context: ComponentContext,
        memberID: MemberID,
    ): Component
}
