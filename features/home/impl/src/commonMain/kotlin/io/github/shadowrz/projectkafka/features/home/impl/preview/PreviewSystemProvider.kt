package io.github.shadowrz.projectkafka.features.home.impl.preview

import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
internal fun aSystem(
    name: String = "???? System",
    description: String? = null,
) = System(
    id = SystemID("1"),
    name = name,
    description = description,
    avatar = null,
    cover = null,
    lastUsed = Instant.fromEpochSeconds(1710630000),
)
