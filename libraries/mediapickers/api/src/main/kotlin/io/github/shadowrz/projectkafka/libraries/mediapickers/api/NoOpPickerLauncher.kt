package io.github.shadowrz.projectkafka.libraries.mediapickers.api

class NoOpPickerLauncher<I, O>(
    private val onResult: () -> Unit,
) : PickerLauncher<I, O> {
    override fun launch() = onResult()

    override fun launch(input: I) = onResult()
}
