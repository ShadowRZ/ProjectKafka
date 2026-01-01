package io.github.shadowrz.projectkafka.features.home.impl.timeline

sealed interface TimelineEvents {
    data class ChangeTimelineType(
        val timelineType: TimelineType,
    ) : TimelineEvents
}
