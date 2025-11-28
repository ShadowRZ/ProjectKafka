package io.github.shadowrz.projectkafka.buildmeta

import androidx.compose.runtime.Immutable

@Immutable
data class BuildMeta(
    val applicationName: String,
    val applicationId: String,
    val versionName: String,
    val versionCode: Int,
)
