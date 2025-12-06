package io.github.shadowrz.projectkafka.libraries.profile.impl

import android.content.Context
import com.attafitamim.krop.core.images.ImageSrc
import com.attafitamim.krop.core.images.toImageSrc
import com.eygraber.uri.Uri
import com.eygraber.uri.toAndroidUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class AndroidImageSrcProvider(
    @ApplicationContext private val context: Context,
) : ImageSrcProvider {
    override suspend fun uriToImageSrc(uri: Uri): ImageSrc? = uri.toAndroidUri().toImageSrc(context = context)
}
