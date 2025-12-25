package io.github.shadowrz.projectkafka.features.home.impl.timeline.frontlog

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.FrontLog

@Stable
data class FrontLogsState(
    val frontLogs: Result<List<FrontLog>>,
) : HanekokoroState
