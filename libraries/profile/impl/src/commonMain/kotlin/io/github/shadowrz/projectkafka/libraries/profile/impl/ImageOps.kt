package io.github.shadowrz.projectkafka.libraries.profile.impl

import androidx.compose.ui.graphics.ImageBitmap
import okio.BufferedSink

expect fun ImageBitmap.compressTo(sink: BufferedSink)
