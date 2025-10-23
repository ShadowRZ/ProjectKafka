package io.github.shadowrz.projectkafka.libraries.profile.api

import androidx.compose.runtime.Stable
import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.imageCropper
import com.eygraber.uri.Uri

@Stable
data class SelectImageState(
    val value: Uri = Uri.EMPTY,
    val imageCropper: ImageCropper = imageCropper(),
    val fromCamera: () -> Unit = {},
    val fromGallery: () -> Unit = {},
    val clear: () -> Unit = {},
)
