package io.github.shadowrz.projectkafka.libraries.localepicker

import androidx.compose.runtime.Stable

@Stable
interface LocalePickerState {
    fun openLocalePicker()

    object EMPTY : LocalePickerState {
        override fun openLocalePicker() {}
    }
}
