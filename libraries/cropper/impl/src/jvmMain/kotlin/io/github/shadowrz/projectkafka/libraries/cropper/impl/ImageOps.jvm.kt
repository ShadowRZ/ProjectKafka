package io.github.shadowrz.projectkafka.libraries.cropper.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.compose.PickerResultLauncher
import okio.BufferedSink
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image

internal actual fun ImageBitmap.compressTo(sink: BufferedSink) {
    val image = Image.makeFromBitmap(asSkiaBitmap())
    val data = image.encodeToData(format = EncodedImageFormat.WEBP)
    data?.let {
        sink.write(it.bytes)
    }
}

@Composable
internal actual fun rememberCameraPickerLauncher(onResult: (PlatformFile?) -> Unit): PickerResultLauncher = PickerResultLauncher {}
