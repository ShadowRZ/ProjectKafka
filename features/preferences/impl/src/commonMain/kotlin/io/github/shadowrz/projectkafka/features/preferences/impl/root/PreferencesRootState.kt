package io.github.shadowrz.projectkafka.features.preferences.impl.root

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

@Stable
data class PreferencesRootState(
    val allowsMultiSystem: Boolean,
    val useSystemFont: Boolean,
    val eventSink: (PreferencesRootEvents) -> Unit,
) : HanekokoroState
