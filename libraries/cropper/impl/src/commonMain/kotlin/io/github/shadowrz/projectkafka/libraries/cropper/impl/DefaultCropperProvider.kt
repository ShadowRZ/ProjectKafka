package io.github.shadowrz.projectkafka.libraries.cropper.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.crop
import com.attafitamim.krop.core.crop.imageCropper
import com.attafitamim.krop.filekit.toImageSrc
import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.hanekokoro.framework.runtime.retain.retainCoroutineScope
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.shadowrz.projectkafka.libraries.fileutils.writeTempFile
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultCropperProvider(
    private val fileSystem: FileSystem,
    @CacheDirectory private val cacheDir: Path,
    private val coroutineDispatchers: CoroutineDispatchers,
) : CropperProvider {
    @Composable
    override fun rememberCropperState(onNewUri: (Uri) -> Unit): CropperState {
        val scope = retainCoroutineScope()
        val cropper = retain { imageCropper() }

        fun onResult(selected: PlatformFile?) {
            scope.launch {
                when (val result = cropper.crop(selected?.toImageSrc())) {
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

        val galleryPicker = rememberFilePickerLauncher(
            type = FileKitType.Image,
            onResult = ::onResult,
        )
        val cameraPicker = rememberCameraPickerLauncher(
            onResult = ::onResult,
        )

        return remember {
            CropperState(
                cropper = cropper,
                fromCamera = { cameraPicker.launch() },
                fromGallery = { galleryPicker.launch() },
            )
        }
    }
}
