package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import com.eygraber.uri.Uri
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Stable
data class System(
    val id: SystemID,
    val name: String,
    val description: String?,
    val avatar: Uri?,
    val cover: Uri?,
    val lastUsed: Instant,
)
