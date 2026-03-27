package io.github.shadowrz.projectkafka.designsystem.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalInspectionMode
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import io.github.shadowrz.projectkafka.assets.SharedDrawables
import io.github.shadowrz.projectkafka.assets.sample_image
import org.jetbrains.compose.resources.imageResource

@OptIn(ExperimentalCoilApi::class)
@Composable
internal actual fun CompositionLocals(content: @Composable (() -> Unit)) {
    val bitmap = imageResource(SharedDrawables.sample_image)

    CompositionLocalProvider(
        LocalInspectionMode provides true,
        LocalAsyncImagePreviewHandler provides AsyncImagePreviewHandler {
            bitmap.asAndroidBitmap().asImage()
        },
        content = content,
    )
}
