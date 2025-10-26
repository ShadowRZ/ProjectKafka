package io.github.shadowrz.projectkafka.libraries.profile.impl

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import okio.Sink
import okio.buffer
import java.io.OutputStream

internal fun ImageBitmap.compressWebP(
    quality: Int,
    sink: Sink,
) = compressWebP(quality, sink.buffer().outputStream())

internal fun ImageBitmap.compressWebP(
    quality: Int,
    output: OutputStream,
) = asAndroidBitmap().compress(Bitmap.CompressFormat.WEBP, quality, output)
