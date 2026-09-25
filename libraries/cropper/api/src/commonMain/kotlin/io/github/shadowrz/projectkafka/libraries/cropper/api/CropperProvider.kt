package io.github.shadowrz.projectkafka.libraries.cropper.api

import androidx.compose.runtime.Composable

interface CropperProvider {
    @Composable fun rememberCropperState(onNewData: (String) -> Unit): CropperState
}
