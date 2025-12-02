package io.github.shadowrz.projectkafka.features.preferences.impl.root

import androidx.compose.runtime.Stable

@Stable
data class PreferencesRootState(
    val allowsMultiSystem: Boolean,
    val eventSink: (PreferencesRootEvents) -> Unit,
)
