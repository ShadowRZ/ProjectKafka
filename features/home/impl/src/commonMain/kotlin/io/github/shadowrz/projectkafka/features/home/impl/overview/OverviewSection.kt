package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.icons.Construction
import io.github.shadowrz.projectkafka.designsystem.icons.GroupOutline
import org.jetbrains.compose.resources.StringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.dashboard_section_members
import projectkafka.features.home.impl.generated.resources.dashboard_section_tools

enum class OverviewSection(
    val desc: StringResource,
    val imageVector: ImageVector,
) {
    Members(
        desc = Res.string.dashboard_section_members,
        imageVector = KafkaIcons.GroupOutline,
    ),

//    Fronters(
//        desc = Res.string.dashboard_section_fronters,
//        imageVector = KafkaIcons.GroupsOutline,
//    ),

    Tools(
        desc = Res.string.dashboard_section_tools,
        imageVector = KafkaIcons.Construction,
    ),
}
