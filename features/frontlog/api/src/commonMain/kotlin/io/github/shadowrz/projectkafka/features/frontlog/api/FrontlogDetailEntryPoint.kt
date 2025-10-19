package io.github.shadowrz.projectkafka.features.frontlog.api

import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin

interface FrontlogDetailEntryPoint : FeatureEntryPoint {
    data object Params : Plugin
}
