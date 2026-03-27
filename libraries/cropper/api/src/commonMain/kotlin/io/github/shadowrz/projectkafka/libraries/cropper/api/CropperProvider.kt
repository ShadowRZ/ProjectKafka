package io.github.shadowrz.projectkafka.libraries.cropper.api

import androidx.compose.runtime.Composable
import com.eygraber.uri.Uri

interface CropperProvider {
    @Composable
    fun rememberCropperState(onNewUri: (Uri) -> Unit): CropperState
}
