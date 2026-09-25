package io.github.shadowrz.projectkafka.libraries.cropper.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import com.attafitamim.krop.core.crop.imageCropper
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FakeCropperProvider : CropperProvider {
    val value: MutableStateFlow<String> = MutableStateFlow("")

    @Composable
    override fun rememberCropperState(onNewData: (String) -> Unit): CropperState {
        val onNewData = rememberUpdatedState(onNewData)

        LaunchedEffect(Unit) {
            value.onEach { if (it != "") onNewData(it) }.launchIn(this)
        }

        return CropperState(
            cropper = imageCropper(),
            fromCamera = {},
            fromGallery = {},
        )
    }
}
