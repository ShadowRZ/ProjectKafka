package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Stable
data class System(
    val id: SystemID,
    val name: String,
    val description: String?,
    val avatar: MediaFile?,
    val cover: MediaFile?,
    val lastUsed: Instant,
)
