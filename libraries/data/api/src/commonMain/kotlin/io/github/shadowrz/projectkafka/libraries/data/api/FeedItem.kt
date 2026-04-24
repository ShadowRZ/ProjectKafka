package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Stable
data class FeedItem(
    val id: FeedID,
    val member: Member,
    val content: String,
    val images: List<MediaFile>,
    val timestamp: Instant,
    val pinned: Boolean,
    val mentions: List<Member>,
)
