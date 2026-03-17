package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.Result

@Stable
data class SwitchSystemState(
    val systems: Result<List<System>>,
    val eventSink: (SwitchSystemEvents) -> Unit,
) : HanekokoroState
