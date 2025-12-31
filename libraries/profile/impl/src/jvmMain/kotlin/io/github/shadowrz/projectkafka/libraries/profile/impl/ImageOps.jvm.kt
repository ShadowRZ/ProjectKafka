package io.github.shadowrz.projectkafka.libraries.profile.impl

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import okio.BufferedSink
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image

actual fun ImageBitmap.compressTo(sink: BufferedSink) {
    val image = Image.makeFromBitmap(asSkiaBitmap())
    val data = image.encodeToData(format = EncodedImageFormat.WEBP)
    data?.let {
        sink.write(it.bytes)
    }
}
