package io.github.shadowrz.projectkafka.features.share.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Parameters

interface ShareEntryPoint : FeatureEntryPoint {
    data class Params(
        val shareData: ShareData,
    ) : Parameters

    fun build(
        parent: Component,
        context: ComponentContext,
        shareData: ShareData,
    ): Component
}
