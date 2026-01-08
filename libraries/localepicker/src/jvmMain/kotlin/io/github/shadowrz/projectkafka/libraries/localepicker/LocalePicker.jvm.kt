package io.github.shadowrz.projectkafka.libraries.localepicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun rememberLocalePicker(): LocalePickerState = LocalePickerState.EMPTY

@Composable
actual fun LocalePicker(
    state: LocalePickerState,
    modifier: Modifier,
) {
    // Empty currently.
}
