package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable

@Stable
data class Poll(
    val id: PollID,
    val content: String,
)
