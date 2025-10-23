package io.github.shadowrz.projectkafka.libraries.profile.impl

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.crop
import com.attafitamim.krop.core.images.toImageSrc
import com.eygraber.uri.Uri
import com.eygraber.uri.toAndroidUri
import com.eygraber.uri.toKmpUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectImageState
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectProfileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.security.DigestOutputStream
import java.security.MessageDigest
import android.net.Uri as AndroidUri

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultSelectProfileProvider : SelectProfileProvider {
    private val digest = MessageDigest.getInstance("SHA-256")

    @Composable
    override fun rememberSelectAvatarState(
        scope: CoroutineScope,
        imageCropper: ImageCropper,
        pickerProvider: PickerProvider,
        initialValue: Uri,
    ): SelectImageState {
        val context = LocalContext.current
        var value by rememberSaveable { mutableStateOf(initialValue.toAndroidUri()) }

        fun onResult(selected: Uri?) {
            scope.launch {
                selected?.let {
                    val result = imageCropper.crop(it.toAndroidUri().toImageSrc(context))
                    when (result) {
                        is CropResult.Success -> {
                            val temp = context.newTempFile()
                            val bitmap = result.bitmap.asAndroidBitmap()
                            val stream = DigestOutputStream(FileOutputStream(temp), digest)
                            stream.use { stream ->
                                bitmap.compress(
                                    Bitmap.CompressFormat.WEBP,
                                    100,
                                    stream,
                                )
                            }
                            val file = context.newAssetFile(stream.messageDigest.digest().toHexString())
                            temp.copyTo(file, true)
                            temp.delete()
                            value = file.toUri()
                        }
                        else -> {}
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
            SelectImageState(
                value = value.toKmpUri(),
                imageCropper = imageCropper,
                fromCamera = { cameraPicker.launch() },
                fromGallery = { galleryPicker.launch() },
                clear = {
                    File(value.path!!).delete()
                    value = AndroidUri.EMPTY
                },
            )
        }
    }

    @Composable
    override fun rememberSelectCoverState(
        scope: CoroutineScope,
        imageCropper: ImageCropper,
        pickerProvider: PickerProvider,
        initialValue: Uri,
    ): SelectImageState {
        val context = LocalContext.current
        var value by rememberSaveable { mutableStateOf(initialValue.toAndroidUri()) }

        fun onResult(selected: Uri?) {
            scope.launch {
                selected?.let {
                    val result = imageCropper.crop(it.toAndroidUri().toImageSrc(context))
                    when (result) {
                        is CropResult.Success -> {
                            val temp = context.newTempFile()
                            val bitmap = result.bitmap.asAndroidBitmap()
                            val stream = DigestOutputStream(FileOutputStream(temp), digest)
                            stream.use { stream ->
                                bitmap.compress(
                                    Bitmap.CompressFormat.WEBP,
                                    100,
                                    stream,
                                )
                            }
                            val file = context.newAssetFile(stream.messageDigest.digest().toHexString())
                            temp.copyTo(file, true)
                            temp.delete()
                            value = file.toUri()
                        }
                        else -> {}
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
            SelectImageState(
                value = value.toKmpUri(),
                imageCropper = imageCropper,
                fromCamera = { cameraPicker.launch() },
                fromGallery = { galleryPicker.launch() },
                clear = {
                    File(value.path!!).delete()
                    value = AndroidUri.EMPTY
                },
            )
        }
    }
}

private fun Context.newTempFile(): File = File.createTempFile("convert", ".webp", cacheDir)

private fun Context.newAssetFile(name: String): File {
    val file = File(filesDir, "assets/$name.webp")
    file.parentFile?.mkdirs()
    return file
}
