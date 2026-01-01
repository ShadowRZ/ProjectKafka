package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Construction
import io.github.shadowrz.projectkafka.libraries.icons.material.GroupOutline
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
        imageVector = MaterialIcons.GroupOutline,
    ),

//    Fronters(
//        desc = Res.string.dashboard_section_fronters,
//        imageVector = MaterialIcons.GroupsOutline,
//    ),

    Tools(
        desc = Res.string.dashboard_section_tools,
        imageVector = MaterialIcons.Construction,
    ),
}
