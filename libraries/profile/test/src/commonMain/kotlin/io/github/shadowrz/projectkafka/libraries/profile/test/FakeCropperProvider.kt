package io.github.shadowrz.projectkafka.libraries.profile.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.attafitamim.krop.core.crop.ImageCropper
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperState
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCropperProvider : CropperProvider {
    val avatar: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val cover: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)

    @Composable
    override fun rememberCropperState(
        imageCropper: ImageCropper,
        initialValue: Uri,
    ): CropperState {
        val avatar by avatar.collectAsState()

        return CropperState(value = avatar)
    }
}
