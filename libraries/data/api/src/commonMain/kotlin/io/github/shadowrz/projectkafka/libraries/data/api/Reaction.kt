package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable

@Stable
data class Reaction(
    val id: ReactionID,
    val content: String,
)
