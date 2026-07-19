package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.System

@Stable data class SwitchSystemState(val systems: AsyncOutcome<List<System>>) : HanekokoroState
