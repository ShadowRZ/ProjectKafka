package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Stable
data class ChatMessage(
    val id: MessageID,
    val member: Member,
    val content: String,
    val media: MediaFile?,
    val timestamp: Instant,
)
