package io.github.shadowrz.projectkafka.libraries.profile.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.attafitamim.krop.core.crop.ImageCropper
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectImageState
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectProfileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSelectProfileProvider : SelectProfileProvider {
    val avatar: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val cover: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)

    @Composable
    override fun rememberSelectAvatarState(
        scope: CoroutineScope,
        imageCropper: ImageCropper,
        pickerProvider: PickerProvider,
        initialValue: Uri,
    ): SelectImageState {
        val avatar by avatar.collectAsState()

        return SelectImageState(value = avatar)
    }

    @Composable
    override fun rememberSelectCoverState(
        scope: CoroutineScope,
        imageCropper: ImageCropper,
        pickerProvider: PickerProvider,
        initialValue: Uri,
    ): SelectImageState {
        val cover by cover.collectAsState()

        return SelectImageState(value = cover)
    }
}
