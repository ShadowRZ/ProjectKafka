package io.github.shadowrz.projectkafka.libraries.cropper.impl

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import okio.BufferedSink

internal actual fun ImageBitmap.compressTo(sink: BufferedSink) {
    this.asAndroidBitmap().compress(
        Bitmap.CompressFormat.WEBP,
        100,
        sink.outputStream(),
    )
}
