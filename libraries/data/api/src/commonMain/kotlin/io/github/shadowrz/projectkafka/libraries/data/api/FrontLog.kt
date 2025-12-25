package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Stable
data class FrontLog(
    val id: FrontLogID,
    val timestamp: Instant,
    val description: String?,
    val members: List<Member>,
)
