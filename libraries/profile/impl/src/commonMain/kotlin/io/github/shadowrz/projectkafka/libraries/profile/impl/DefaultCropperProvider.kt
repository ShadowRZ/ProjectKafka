package io.github.shadowrz.projectkafka.libraries.profile.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.crop
import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Buffer
import okio.FileSystem
import okio.HashingSink
import okio.Path
import okio.buffer
import okio.use

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultCropperProvider(
    private val scope: CoroutineScope,
    private val fileSystem: FileSystem,
    @FilesDirectory private val filesDir: Path,
    private val pickerProvider: PickerProvider,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val imageSrcProvider: ImageSrcProvider,
) : CropperProvider {
    @Composable
    override fun rememberCropperState(
        imageCropper: ImageCropper,
        initialValue: Uri,
    ): CropperState {
        var value by rememberSaveable { mutableStateOf(initialValue.toString()) }

        fun onResult(selected: Uri?) {
            scope.launch {
                selected?.let {
                    when (val result = imageCropper.crop(imageSrcProvider.uriToImageSrc(it))) {
                        is CropResult.Success -> {
                            withContext(coroutineDispatchers.io) {
                                val buffer = Buffer()
                                val sink = HashingSink.sha256(buffer)
                                sink.use { sink ->
                                    sink.buffer().use { buffer ->
                                        result.bitmap.compressTo(buffer)
                                        buffer.flush()
                                    }
                                    sink.flush()
                                    val output = filesDir / "assets" / "${sink.hash.hex()}.webp"
                                    fileSystem.write(
                                        output.also { output ->
                                            output.parent?.let { parent ->
                                                fileSystem.createDirectory(parent)
                                            }
                                        },
                                    ) {
                                        writeAll(buffer)
                                        flush()
                                    }
                                    value = output.toString()
                                }
                            }
                        }

                        else -> { /* Nothing to do */ }
                    }
                }
            }
        }

        val galleryPicker =
            pickerProvider.rememberGalleryImagePicker {
                onResult(it)
            }
        val cameraPicker =
            pickerProvider.rememberCameraPhotoPicker {
                onResult(it)
            }

        return remember(value) {
            CropperState(
                value = value.toKmpUri(),
                imageCropper = imageCropper,
                fromCamera = { cameraPicker.launch() },
                fromGallery = { galleryPicker.launch() },
                clear = {
                    value = ""
                },
            )
        }
    }
}
