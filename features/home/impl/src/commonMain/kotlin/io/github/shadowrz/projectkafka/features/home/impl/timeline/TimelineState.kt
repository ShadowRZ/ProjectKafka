package io.github.shadowrz.projectkafka.features.home.impl.timeline

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.features.home.impl.timeline.frontlog.FrontLogsState

@Stable
data class TimelineState(
    val timelineType: TimelineType,
    val frontLogsState: FrontLogsState,
    val eventSink: (TimelineEvents) -> Unit,
) : HanekokoroState
