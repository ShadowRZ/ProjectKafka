package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Stable
data class FrontLog(
    val members: List<Member>,
    val timestamp: Instant,
)
