package io.github.shadowrz.projectkafka.libraries.cropper.impl

import androidx.compose.ui.graphics.ImageBitmap
import okio.BufferedSink

internal expect fun ImageBitmap.compressTo(sink: BufferedSink)
