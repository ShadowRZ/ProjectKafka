package io.github.shadowrz.projectkafka.libraries.mediapickers.impl

import android.content.ActivityNotFoundException
import androidx.activity.compose.ManagedActivityResultLauncher
import co.touchlab.kermit.Logger
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerLauncher

internal class ComposePickerLauncher<I, O>(
    private val launcher: ManagedActivityResultLauncher<I, O>,
    private val defaultInput: I,
) : PickerLauncher<I, O> {
    override fun launch() {
        try {
            launcher.launch(defaultInput)
        } catch (e: ActivityNotFoundException) {
            Logger.w("No activity found!", e)
        }
    }

    override fun launch(input: I) {
        try {
            launcher.launch(input)
        } catch (e: ActivityNotFoundException) {
            Logger.w("No activity found!", e)
        }
    }
}
