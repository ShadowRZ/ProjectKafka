package io.github.shadowrz.projectkafka.libraries.cropper.impl

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.compose.PickerResultLauncher
import okio.BufferedSink

internal actual fun ImageBitmap.compressTo(sink: BufferedSink) {
    this.asAndroidBitmap().compress(
        Bitmap.CompressFormat.WEBP,
        100,
        sink.outputStream(),
    )
}

@Composable
internal actual fun rememberCameraPickerLauncher(onResult: (PlatformFile?) -> Unit): PickerResultLauncher {
    val launcher = io.github.vinceglb.filekit.dialogs.compose
        .rememberCameraPickerLauncher(onResult = onResult)

    return remember(launcher) {
        PickerResultLauncher { launcher.launch() }
    }
}
