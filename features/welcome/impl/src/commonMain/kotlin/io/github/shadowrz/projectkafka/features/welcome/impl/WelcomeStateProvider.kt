package io.github.shadowrz.projectkafka.features.welcome.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.shadowrz.projectkafka.libraries.localepicker.LocalePickerState

internal class WelcomeStateProvider : PreviewParameterProvider<WelcomeState> {
    override val values: Sequence<WelcomeState>
        get() =
            sequenceOf(
                aWelcomeState(),
                aWelcomeState(showHelp = true),
            )
}

private fun aWelcomeState(showHelp: Boolean = false) =
    WelcomeState(
        showHelp = showHelp,
        localePickerState = LocalePickerState.EMPTY,
        eventSink = {},
    )
