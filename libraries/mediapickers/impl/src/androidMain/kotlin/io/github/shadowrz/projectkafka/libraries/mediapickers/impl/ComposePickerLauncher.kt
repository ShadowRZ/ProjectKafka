package io.github.shadowrz.projectkafka.libraries.mediapickers.impl

import android.content.ActivityNotFoundException
import androidx.activity.compose.ManagedActivityResultLauncher
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerLauncher
import timber.log.Timber

internal class ComposePickerLauncher<I, O>(
    private val launcher: ManagedActivityResultLauncher<I, O>,
    private val defaultInput: I,
) : PickerLauncher<I, O> {
    override fun launch() {
        try {
            launcher.launch(defaultInput)
        } catch (e: ActivityNotFoundException) {
            Timber.w(e, "No activity found!")
        }
    }

    override fun launch(input: I) {
        try {
            launcher.launch(input)
        } catch (e: ActivityNotFoundException) {
            Timber.w(e, "No activity found!")
        }
    }
}
