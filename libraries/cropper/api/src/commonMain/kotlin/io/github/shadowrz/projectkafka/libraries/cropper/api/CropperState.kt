package io.github.shadowrz.projectkafka.libraries.cropper.api

import androidx.compose.runtime.Stable
import com.attafitamim.krop.core.crop.ImageCropper

@Stable
data class CropperState(
    val cropper: ImageCropper,
    val fromCamera: () -> Unit,
    val fromGallery: () -> Unit,
)
