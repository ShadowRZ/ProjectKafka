package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.GroupOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.PersonOutline
import org.jetbrains.compose.resources.StringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.chats_filter_group
import projectkafka.features.home.impl.generated.resources.chats_filter_member

enum class ChatsType(
    val desc: StringResource,
    val imageVector: ImageVector,
) {
    Member(
        desc = Res.string.chats_filter_member,
        imageVector = MaterialIcons.PersonOutline,
    ),
    Group(
        desc = Res.string.chats_filter_group,
        imageVector = MaterialIcons.GroupOutline,
    ),
}
