package io.github.shadowrz.projectkafka.features.welcome.impl

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.localepicker.LocalePickerState

@Stable
data class WelcomeState(
    val localePickerState: LocalePickerState,
    val showHelp: Boolean,
    val eventSink: (WelcomeEvents) -> Unit,
) : HanekokoroState
