package io.github.shadowrz.projectkafka.libraries.profile.impl

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import okio.BufferedSink

actual fun ImageBitmap.compressTo(sink: BufferedSink) {
    this.asAndroidBitmap().compress(
        Bitmap.CompressFormat.WEBP,
        100,
        sink.outputStream(),
    )
}
