package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import com.eygraber.uri.Uri

@Stable
data class Chat(
    val id: ChatID,
    val name: String?,
    val avatar: Uri?,
    val creatorID: MemberID,
)
