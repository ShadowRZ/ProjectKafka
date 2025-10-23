package io.github.shadowrz.projectkafka.libraries.mediapickers.impl

import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerLauncher

internal class DefaultOnlyPickerLauncher<I, O>(
    private val proxy: PickerLauncher<I, O>,
) : PickerLauncher<Nothing, O> {
    override fun launch() {
        proxy.launch()
    }

    override fun launch(input: Nothing) = launch()
}
