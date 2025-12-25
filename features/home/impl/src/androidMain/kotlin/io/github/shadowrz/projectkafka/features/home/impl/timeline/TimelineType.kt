package io.github.shadowrz.projectkafka.features.home.impl.timeline

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.shadowrz.projectkafka.features.home.impl.R
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.EventListOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.NoteStackOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.SwitchAccountOutline

enum class TimelineType(
    @StringRes val desc: Int,
    val imageVector: ImageVector,
) {
    Activity(
        desc = R.string.timeline_filter_activity,
        imageVector = MaterialIcons.EventListOutline,
    ),
    FrontLog(
        desc = R.string.timeline_filter_front_log,
        imageVector = MaterialIcons.SwitchAccountOutline,
    ),
    QuickNotes(
        desc = R.string.timeline_filter_quick_notes,
        imageVector = MaterialIcons.NoteStackOutline,
    ),
}
