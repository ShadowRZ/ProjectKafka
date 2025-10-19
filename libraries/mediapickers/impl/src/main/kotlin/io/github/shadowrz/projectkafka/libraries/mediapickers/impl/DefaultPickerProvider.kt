package io.github.shadowrz.projectkafka.libraries.mediapickers.impl

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.core.content.FileProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.NoOpPickerLauncher
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerLauncher
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerType
import java.io.File

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Inject
class DefaultPickerProvider(
    @ApplicationContext private val context: Context,
) : PickerProvider {
    @Composable
    internal fun <I, O> rememberPicker(
        type: PickerType<I, O>,
        onResult: (O) -> Unit,
    ): PickerLauncher<I, O> =
        if (LocalInspectionMode.current) {
            NoOpPickerLauncher {}
        } else {
            val launcher =
                rememberLauncherForActivityResult(
                    contract = type.getContract(),
                    onResult = onResult,
                )
            remember(type) {
                ComposePickerLauncher(
                    launcher = launcher,
                    defaultInput = type.getDefaultInput(),
                )
            }
        }

    @Composable
    override fun rememberGalleryPicker(onResult: (Uri?) -> Unit): PickerLauncher<PickVisualMediaRequest, Uri?> =
        if (LocalInspectionMode.current) {
            NoOpPickerLauncher(onResult = { null })
        } else {
            rememberPicker(
                type = PickerType.ImageAndVideo,
                onResult = onResult,
            )
        }

    @Composable
    override fun rememberGalleryImagePicker(onResult: (Uri?) -> Unit): PickerLauncher<PickVisualMediaRequest, Uri?> =
        if (LocalInspectionMode.current) {
            NoOpPickerLauncher(onResult = { null })
        } else {
            rememberPicker(
                type = PickerType.ImageOnly,
                onResult = onResult,
            )
        }

    @Composable
    override fun rememberFilePicker(
        mimeType: String,
        onResult: (Uri?) -> Unit,
    ): PickerLauncher<String, Uri?> =
        if (LocalInspectionMode.current) {
            NoOpPickerLauncher(onResult = { null })
        } else {
            rememberPicker(
                type = PickerType.File(mimeType = mimeType),
                onResult = onResult,
            )
        }

    @Composable
    override fun rememberCameraPhotoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean> =
        if (LocalInspectionMode.current) {
            NoOpPickerLauncher(onResult = { null })
        } else {
            val file = remember { getTemporaryFile("jpg") }
            val fileUri = remember(file) { getTemporaryUri(file) }
            rememberPicker(
                type = PickerType.Camera.Image(fileUri),
            ) { success ->
                onResult(if (success) fileUri else null)
            }
        }

    @Composable
    override fun rememberCameraVideoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean> =
        if (LocalInspectionMode.current) {
            NoOpPickerLauncher(onResult = { null })
        } else {
            val file = remember { getTemporaryFile("mp4") }
            val fileUri = remember(file) { getTemporaryUri(file) }
            rememberPicker(
                type = PickerType.Camera.Image(fileUri),
            ) { success ->
                onResult(if (success) fileUri else null)
            }
        }

    private fun getTemporaryFile(ext: String): File =
        File.createTempFile(
            "captures_",
            ".$ext",
            context.cacheDir,
        )

    private fun getTemporaryUri(file: File): Uri =
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file,
        )
}
