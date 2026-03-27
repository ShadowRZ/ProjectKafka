package io.github.shadowrz.projectkafka.libraries.cropper.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import com.attafitamim.krop.core.crop.imageCropper
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState

class FakeCropperProvider : CropperProvider {
    val value: MutableState<Uri> = mutableStateOf(Uri.EMPTY)

    @Composable
    override fun rememberCropperState(onNewUri: (Uri) -> Unit): CropperState {
        val value by this.value
        val onNewUri = rememberUpdatedState(onNewUri)

        LaunchedEffect(value) {
            onNewUri(value)
        }

        return CropperState(
            cropper = imageCropper(),
            fromCamera = {},
            fromGallery = {},
        )
    }
}
