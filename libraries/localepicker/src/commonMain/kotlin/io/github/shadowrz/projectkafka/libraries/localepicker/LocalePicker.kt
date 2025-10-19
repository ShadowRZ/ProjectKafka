package io.github.shadowrz.projectkafka.libraries.localepicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun rememberLocalePicker(): LocalePickerState

@Composable
expect fun LocalePicker(
    state: LocalePickerState,
    modifier: Modifier = Modifier,
)
