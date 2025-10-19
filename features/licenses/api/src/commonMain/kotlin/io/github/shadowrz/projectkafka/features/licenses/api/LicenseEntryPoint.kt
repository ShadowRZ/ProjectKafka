package io.github.shadowrz.projectkafka.features.licenses.api

import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint

interface LicenseEntryPoint : FeatureEntryPoint {
    fun build(
        parent: Component,
        context: ComponentContext,
    ): Component
}
