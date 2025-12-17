package io.github.shadowrz.projectkafka.features.about.impl

import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta

data class AboutState(
    val buildMeta: BuildMeta,
    val eventSink: (AboutEvents) -> Unit,
) : HanekokoroState
