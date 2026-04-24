package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable

@Stable
data class Chat(
    val id: ChatID,
    val name: String?,
    val avatar: MediaFile?,
    val creatorID: MemberID,
)
