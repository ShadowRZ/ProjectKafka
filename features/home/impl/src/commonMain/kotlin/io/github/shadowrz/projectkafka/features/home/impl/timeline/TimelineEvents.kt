package io.github.shadowrz.projectkafka.features.home.impl.timeline

expect sealed interface TimelineEvents {
    class ChangeTimelineType : TimelineEvents
}
