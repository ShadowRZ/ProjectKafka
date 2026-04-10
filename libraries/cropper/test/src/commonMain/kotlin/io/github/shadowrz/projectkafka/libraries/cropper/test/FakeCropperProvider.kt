package io.github.shadowrz.projectkafka.libraries.cropper.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import com.attafitamim.krop.core.crop.imageCropper
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FakeCropperProvider : CropperProvider {
    val value: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)

    @Composable
    override fun rememberCropperState(onNewUri: (Uri) -> Unit): CropperState {
        val onNewUri = rememberUpdatedState(onNewUri)

        LaunchedEffect(Unit) {
            value.onEach { if (it != Uri.EMPTY) onNewUri(it) }.launchIn(this)
        }

        return CropperState(
            cropper = imageCropper(),
            fromCamera = {},
            fromGallery = {},
        )
    }
}
