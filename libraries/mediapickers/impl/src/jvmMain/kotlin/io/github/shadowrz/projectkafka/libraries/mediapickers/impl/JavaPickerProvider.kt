package io.github.shadowrz.projectkafka.libraries.mediapickers.impl

import androidx.compose.runtime.Composable
import com.eygraber.uri.Uri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.NoOpPickerLauncher
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerLauncher
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Inject
class JavaPickerProvider : PickerProvider {
    @Composable
    override fun rememberGalleryPicker(onResult: (Uri?) -> Unit): PickerLauncher<Nothing, Uri?> = NoOpPickerLauncher {}

    @Composable
    override fun rememberGalleryImagePicker(onResult: (Uri?) -> Unit): PickerLauncher<Nothing, Uri?> = NoOpPickerLauncher {}

    @Composable
    override fun rememberFilePicker(
        mimeType: String,
        onResult: (Uri?) -> Unit,
    ): PickerLauncher<String, Uri?> = NoOpPickerLauncher {}

    @Composable
    override fun rememberCameraPhotoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean> = NoOpPickerLauncher {}

    @Composable
    override fun rememberCameraVideoPicker(onResult: (Uri?) -> Unit): PickerLauncher<Uri, Boolean> = NoOpPickerLauncher {}
}
