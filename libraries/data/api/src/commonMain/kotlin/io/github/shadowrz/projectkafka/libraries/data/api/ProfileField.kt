package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable

@Stable
data class ProfileField(
    val name: String,
    val content: String?,
)
