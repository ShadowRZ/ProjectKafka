package io.github.shadowrz.projectkafka.libraries.mediapickers.api

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.runtime.Composable

/**
 * Media pickers.
 */
interface PickerProvider {
    @Composable
    fun rememberGalleryPicker(onResult: (Uri?) -> Unit): PickerLauncher<PickVisualMediaRequest, Uri?>

    @Composable
    fun rememberGalleryImagePicker(onResult: (Uri?) -> Unit): PickerLauncher<PickVisualMediaRequest, Uri?>

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
