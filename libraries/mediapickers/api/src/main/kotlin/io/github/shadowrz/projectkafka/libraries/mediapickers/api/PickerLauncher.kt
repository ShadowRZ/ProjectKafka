package io.github.shadowrz.projectkafka.libraries.mediapickers.api

/**
 * Abstracted media picker contracts.
 */
interface PickerLauncher<I, O> {
    /**
     * Starts the picker with default input paramters.
     */
    fun launch()

    /**
     * Starts the picker with provided input paramters.
     * @param input Custom paramaters.
     */
    fun launch(input: I)
}
