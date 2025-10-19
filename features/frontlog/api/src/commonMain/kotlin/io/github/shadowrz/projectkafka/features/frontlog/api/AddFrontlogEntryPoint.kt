package io.github.shadowrz.projectkafka.features.frontlog.api

import io.github.shadowrz.projectkafka.libraries.architecture.FeatureEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

interface AddFrontlogEntryPoint : FeatureEntryPoint {
    interface Callback : Plugin {
        fun onFinished(id: SystemID)
    }
}
