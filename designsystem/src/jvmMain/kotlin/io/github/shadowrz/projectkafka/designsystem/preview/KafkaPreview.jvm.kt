package io.github.shadowrz.projectkafka.designsystem.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.platform.LocalInspectionMode
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import org.jetbrains.compose.resources.imageResource
import projectkafka.designsystem.generated.resources.Res
import projectkafka.designsystem.generated.resources.sample_image

@OptIn(ExperimentalCoilApi::class)
@Composable
internal actual fun CompositionLocals(content: @Composable (() -> Unit)) {
    val bitmap = imageResource(Res.drawable.sample_image)

    CompositionLocalProvider(
        LocalInspectionMode provides true,
        LocalAsyncImagePreviewHandler provides AsyncImagePreviewHandler {
            bitmap.asSkiaBitmap().asImage()
        },
        content = content,
    )
}
