package io.github.shadowrz.projectkafka.libraries.profile.api

import androidx.compose.runtime.Composable
import com.attafitamim.krop.core.crop.ImageCropper
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import kotlinx.coroutines.CoroutineScope

interface SelectProfileProvider {
    @Composable
    fun rememberSelectAvatarState(
        scope: CoroutineScope,
        imageCropper: ImageCropper,
        pickerProvider: PickerProvider,
        initialValue: Uri = Uri.EMPTY,
    ): SelectImageState

    @Composable
    fun rememberSelectCoverState(
        scope: CoroutineScope,
        imageCropper: ImageCropper,
        pickerProvider: PickerProvider,
        initialValue: Uri = Uri.EMPTY,
    ): SelectImageState
}
