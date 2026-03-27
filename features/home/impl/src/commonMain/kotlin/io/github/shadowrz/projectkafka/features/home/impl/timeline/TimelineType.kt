package io.github.shadowrz.projectkafka.features.home.impl.timeline

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.icons.EventListOutline
import io.github.shadowrz.projectkafka.designsystem.icons.NoteStackOutline
import io.github.shadowrz.projectkafka.designsystem.icons.SwitchAccountOutline
import org.jetbrains.compose.resources.StringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.timeline_filter_activity
import projectkafka.features.home.impl.generated.resources.timeline_filter_front_log
import projectkafka.features.home.impl.generated.resources.timeline_filter_quick_notes

enum class TimelineType(
    val desc: StringResource,
    val imageVector: ImageVector,
) {
    Activity(
        desc = Res.string.timeline_filter_activity,
        imageVector = KafkaIcons.EventListOutline,
    ),
    FrontLog(
        desc = Res.string.timeline_filter_front_log,
        imageVector = KafkaIcons.SwitchAccountOutline,
    ),
    QuickNotes(
        desc = Res.string.timeline_filter_quick_notes,
        imageVector = KafkaIcons.NoteStackOutline,
    ),
}
