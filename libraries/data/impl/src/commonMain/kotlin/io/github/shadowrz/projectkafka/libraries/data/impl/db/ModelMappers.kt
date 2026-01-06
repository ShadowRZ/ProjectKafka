package io.github.shadowrz.projectkafka.libraries.data.impl.db

import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.data.api.Chat as ModelChat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage as ModelChatMessage
import io.github.shadowrz.projectkafka.libraries.data.api.Member as ModelMember
import io.github.shadowrz.projectkafka.libraries.data.api.System as ModelSystem
import io.github.shadowrz.projectkafka.libraries.data.impl.db.Chat as DbChat
import io.github.shadowrz.projectkafka.libraries.data.impl.db.Member as DbMember
import io.github.shadowrz.projectkafka.libraries.data.impl.db.Message as DbMessage
import io.github.shadowrz.projectkafka.libraries.data.impl.db.System as DbSystem

internal fun ModelChat.toDbModel() =
    DbChat(
        id = id.value,
        name = name,
        creatorId = creatorID.value,
        avatar = avatar,
    )

internal fun ModelSystem.toDbModel() =
    DbSystem(
        id = id.value,
        name = name,
        description = description,
        avatar = avatar,
        cover = cover,
        lastUsed = lastUsed,
    )

internal fun ModelChatMessage.toDbModel(
    chatID: ChatID,
    contentId: Long,
) = DbMessage(
    id = id.value,
    chatId = chatID.value,
    memberId = member.id.value,
    contentId = contentId,
    media = media,
    timestamp = timestamp,
)

internal fun ModelMember.toDbModel() =
    DbMember(
        id = id.value,
        name = name,
        description = description,
        avatar = avatar,
        cover = cover,
        preferences = preferences,
        roles = roles,
        birth = birth,
        admin = admin,
    )
