package io.github.shadowrz.projectkafka.libraries.profile.api

import androidx.compose.runtime.Composable
import com.attafitamim.krop.core.crop.ImageCropper
import com.eygraber.uri.Uri

interface CropperProvider {
    @Composable
    fun rememberCropperState(
        imageCropper: ImageCropper,
        initialValue: Uri = Uri.EMPTY,
    ): CropperState
}
