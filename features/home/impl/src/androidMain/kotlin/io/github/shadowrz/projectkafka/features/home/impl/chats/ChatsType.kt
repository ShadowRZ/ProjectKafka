package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.shadowrz.projectkafka.features.home.impl.R
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.GroupOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.PersonOutline

enum class ChatsType(
    @StringRes val desc: Int,
    val imageVector: ImageVector,
) {
    Member(
        desc = R.string.chats_filter_member,
        imageVector = MaterialIcons.PersonOutline,
    ),
    Group(
        desc = R.string.chats_filter_group,
        imageVector = MaterialIcons.GroupOutline,
    ),
}
