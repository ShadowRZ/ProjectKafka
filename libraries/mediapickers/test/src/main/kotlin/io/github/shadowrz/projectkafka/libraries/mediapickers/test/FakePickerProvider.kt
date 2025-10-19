package io.github.shadowrz.projectkafka.libraries.mediapickers.test

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.runtime.Composable
import io.github.shadowrz.projectkafka.libraries.core.mimetypes.MimeTypes
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.NoOpPickerLauncher
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerLauncher
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider

class FakePickerProvider : PickerProvider {
    private var mimeType = MimeTypes.Any
    private var result: Uri? = null

    @Composable
    override fun rememberGalleryPicker(onResult: (Uri?) -> Unit): PickerLauncher<PickVisualMediaRequest, Uri?> =
        NoOpPickerLauncher { onResult(result) }

    @Composable
    override fun rememberGalleryImagePicker(onResult: (Uri?) -> Unit): PickerLauncher<PickVisualMediaRequest, Uri?> =
        NoOpPickerLauncher { onResult(result) }

    @Composable
    override fun rememberFilePicker(
        mimeType: String,
        onResult: (Uri?) -> Unit,
    ): PickerLauncher<String, Uri?> = NoOpPickerLauncher { onResult(result) }

    @Composable
    override fun rememberCameraPhotoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean> = NoOpPickerLauncher { onResult(result) }

    @Composable
    override fun rememberCameraVideoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean> = NoOpPickerLauncher { onResult(result) }

    fun givenResult(result: Uri?) {
        this.result = result
    }

    fun givenMimeType(mimeType: String) {
        this.mimeType = mimeType
    }
}
