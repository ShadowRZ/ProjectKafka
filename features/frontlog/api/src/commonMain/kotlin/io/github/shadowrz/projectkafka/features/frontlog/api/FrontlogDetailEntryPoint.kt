package io.github.shadowrz.projectkafka.features.frontlog.api

import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint

interface FrontlogDetailEntryPoint : FeatureEntryPoint {
    data object Params : Plugin
}
