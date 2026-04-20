package io.github.shadowrz.projectkafka.libraries.cropper.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.compose.PickerResultLauncher
import okio.BufferedSink

internal expect fun ImageBitmap.compressTo(sink: BufferedSink)

@Composable
internal expect fun rememberCameraPickerLauncher(onResult: (PlatformFile?) -> Unit): PickerResultLauncher
