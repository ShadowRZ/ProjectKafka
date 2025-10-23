package io.github.shadowrz.projectkafka.libraries.mediapickers.api

import androidx.compose.runtime.Composable
import com.eygraber.uri.Uri

/**
 * Media pickers.
 */
interface PickerProvider {
    @Composable
    fun rememberGalleryPicker(onResult: (Uri?) -> Unit): PickerLauncher<Nothing, Uri?>

    @Composable
    fun rememberGalleryImagePicker(onResult: (Uri?) -> Unit): PickerLauncher<Nothing, Uri?>

    @Composable
    fun rememberFilePicker(
        mimeType: String,
        onResult: (Uri?) -> Unit,
    ): PickerLauncher<String, Uri?>

    @Composable
    fun rememberCameraPhotoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean>

    @Composable
    fun rememberCameraVideoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean>
}
