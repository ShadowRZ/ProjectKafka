package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.shadowrz.projectkafka.features.home.impl.R
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Construction
import io.github.shadowrz.projectkafka.libraries.icons.material.GroupOutline

enum class OverviewSection(
    @StringRes val desc: Int,
    val imageVector: ImageVector,
) {
    Members(
        desc = R.string.dashboard_section_members,
        imageVector = MaterialIcons.GroupOutline,
    ),

//    Fronters(
//        desc = R.string.dashboard_section_fronters,
//        imageVector = MaterialIcons.GroupsOutline,
//    ),

    Tools(
        desc = R.string.dashboard_section_tools,
        imageVector = MaterialIcons.Construction,
    ),
}
