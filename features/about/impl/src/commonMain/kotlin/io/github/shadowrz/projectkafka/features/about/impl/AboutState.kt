package io.github.shadowrz.projectkafka.features.about.impl

import androidx.compose.runtime.Stable
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta

@Stable
data class AboutState(
    val buildMeta: BuildMeta,
    val eventSink: (AboutEvents) -> Unit,
)
