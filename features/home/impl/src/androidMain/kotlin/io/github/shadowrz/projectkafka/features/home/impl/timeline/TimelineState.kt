package io.github.shadowrz.projectkafka.features.home.impl.timeline

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

@Stable
actual data class TimelineState(
    val timelineType: TimelineType,
    val eventSink: (TimelineEvents) -> Unit,
) : HanekokoroState
