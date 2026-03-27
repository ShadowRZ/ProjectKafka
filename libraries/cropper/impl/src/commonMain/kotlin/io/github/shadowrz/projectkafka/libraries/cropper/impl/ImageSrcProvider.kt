package io.github.shadowrz.projectkafka.libraries.cropper.impl

import com.attafitamim.krop.core.images.ImageSrc
import com.eygraber.uri.Uri

fun interface ImageSrcProvider {
    suspend fun uriToImageSrc(uri: Uri): ImageSrc?
}
