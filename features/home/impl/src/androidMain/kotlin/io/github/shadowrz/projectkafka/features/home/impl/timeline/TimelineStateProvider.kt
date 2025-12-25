package io.github.shadowrz.projectkafka.features.home.impl.timeline

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class TimelineStateProvider : PreviewParameterProvider<TimelineState> {
    override val values: Sequence<TimelineState>
        get() =
            sequenceOf(
                aTimelineState(),
                aTimelineState(timelineType = TimelineType.FrontLog),
                aTimelineState(timelineType = TimelineType.QuickNotes),
            )
}

private fun aTimelineState(timelineType: TimelineType = TimelineType.Activity): TimelineState =
    TimelineState(
        timelineType = timelineType,
    ) {
    }
