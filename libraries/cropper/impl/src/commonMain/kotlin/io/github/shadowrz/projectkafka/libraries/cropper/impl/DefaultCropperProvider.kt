package io.github.shadowrz.projectkafka.libraries.cropper.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.crop
import com.attafitamim.krop.core.crop.imageCropper
import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.shadowrz.projectkafka.libraries.fileutils.writeTempFile
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultCropperProvider(
    private val scope: CoroutineScope,
    private val fileSystem: FileSystem,
    @CacheDirectory private val cacheDir: Path,
    private val pickerProvider: PickerProvider,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val imageSrcProvider: ImageSrcProvider,
) : CropperProvider {
    @Composable
    override fun rememberCropperState(onNewUri: (Uri) -> Unit): CropperState {
        val cropper = retain { imageCropper() }

        fun onResult(selected: Uri?) {
            scope.launch {
                selected?.let {
                    when (val result = cropper.crop(imageSrcProvider.uriToImageSrc(it))) {
                        is CropResult.Success -> {
                            withContext(coroutineDispatchers.io) {
                                val path = fileSystem.writeTempFile(cacheDir, "webp") {
                                    result.bitmap.compressTo(this)
                                    flush()
                                }
                                onNewUri(path.toString().toKmpUri())
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

        return remember {
            CropperState(
                cropper = cropper,
                fromCamera = { cameraPicker.launch() },
                fromGallery = { galleryPicker.launch() },
            )
        }
    }
}
