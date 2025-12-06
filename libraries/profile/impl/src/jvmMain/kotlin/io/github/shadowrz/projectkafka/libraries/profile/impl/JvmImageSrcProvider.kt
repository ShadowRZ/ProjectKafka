package io.github.shadowrz.projectkafka.libraries.profile.impl

import com.attafitamim.krop.core.images.ImageSrc
import com.attafitamim.krop.core.images.toImageSrc
import com.eygraber.uri.Uri
import com.eygraber.uri.toURI
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlin.io.path.toPath

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class JvmImageSrcProvider : ImageSrcProvider {
    override suspend fun uriToImageSrc(uri: Uri): ImageSrc? =
        uri
            .toURI()
            .toPath()
            .toFile()
            .toImageSrc()
}
