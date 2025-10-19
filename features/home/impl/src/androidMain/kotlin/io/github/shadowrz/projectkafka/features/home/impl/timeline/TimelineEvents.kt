package io.github.shadowrz.projectkafka.features.home.impl.timeline

actual sealed interface TimelineEvents {
    actual data class ChangeTimelineType(
        val timelineType: TimelineType,
    ) : TimelineEvents
}
