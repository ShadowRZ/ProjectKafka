package io.github.shadowrz.projectkafka.libraries.mediapickers.api

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import io.github.shadowrz.projectkafka.libraries.core.mimetypes.MimeTypes

sealed interface PickerType<I, O> {
    fun getContract(): ActivityResultContract<I, O>

    fun getDefaultInput(): I

    data object ImageOnly : PickerType<PickVisualMediaRequest, Uri?> {
        override fun getContract(): ActivityResultContract<PickVisualMediaRequest, Uri?> = ActivityResultContracts.PickVisualMedia()

        override fun getDefaultInput(): PickVisualMediaRequest =
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly,
            )
    }

    data object VideoOnly : PickerType<PickVisualMediaRequest, Uri?> {
        override fun getContract(): ActivityResultContract<PickVisualMediaRequest, Uri?> = ActivityResultContracts.PickVisualMedia()

        override fun getDefaultInput(): PickVisualMediaRequest =
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.VideoOnly,
            )
    }

    data object ImageAndVideo : PickerType<PickVisualMediaRequest, Uri?> {
        override fun getContract(): ActivityResultContract<PickVisualMediaRequest, Uri?> = ActivityResultContracts.PickVisualMedia()

        override fun getDefaultInput(): PickVisualMediaRequest =
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageAndVideo,
            )
    }

    object Camera {
        data class Image(
            val output: Uri,
        ) : PickerType<Uri, Boolean> {
            override fun getContract(): ActivityResultContract<Uri, Boolean> = ActivityResultContracts.TakePicture()

            override fun getDefaultInput(): Uri = output
        }

        data class Video(
            val output: Uri,
        ) : PickerType<Uri, Boolean> {
            override fun getContract(): ActivityResultContract<Uri, Boolean> = ActivityResultContracts.CaptureVideo()

            override fun getDefaultInput(): Uri = output
        }
    }

    data class File(
        val mimeType: String = MimeTypes.Any,
    ) : PickerType<String, Uri?> {
        override fun getContract(): ActivityResultContract<String, Uri?> = ActivityResultContracts.GetContent()

        override fun getDefaultInput(): String = mimeType
    }
}
