package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import com.eygraber.uri.Uri
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Stable
data class ChatMessage(
    val id: MessageID,
    val member: Member,
    val content: String,
    val media: Uri?,
    val timestamp: Instant,
)
