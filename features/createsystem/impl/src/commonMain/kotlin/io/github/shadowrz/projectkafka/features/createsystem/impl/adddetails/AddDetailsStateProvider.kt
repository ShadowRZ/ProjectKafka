package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.attafitamim.krop.core.crop.imageCropper
import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState
import io.github.shadowrz.projectkafka.libraries.kafkaui.AvatarPickerState
import io.github.shadowrz.projectkafka.libraries.kafkaui.CoverPickerState

class AddDetailsStateProvider : PreviewParameterProvider<AddDetailsState> {
    override val values: Sequence<AddDetailsState>
        get() =
            sequenceOf(
                aAddDetailsState(),
                aAddDetailsState(loading = true),
                aFilledAddDetailsState(),
                aFilledAddDetailsState(loading = true),
            )
}

private fun aAddDetailsState(loading: Boolean = false) =
    AddDetailsState(
        avatar = Uri.EMPTY,
        cover = Uri.EMPTY,
        coverCropper = CropperState(
            cropper = imageCropper(),
            fromCamera = {},
            fromGallery = {},
        ),
        avatarCropper = CropperState(
            cropper = imageCropper(),
            fromCamera = {},
            fromGallery = {},
        ),
        systemName = "???? System",
        loading = loading,
        showAvatarSheet = false,
        showCoverSheet = false,
        showCamera = true,
    ) {}

private fun aFilledAddDetailsState(loading: Boolean = false) =
    AddDetailsState(
        avatar = "dummy".toKmpUri(),
        cover = "dummy".toKmpUri(),
        coverCropper = CropperState(
            cropper = imageCropper(),
            fromCamera = {},
            fromGallery = {},
        ),
        avatarCropper = CropperState(
            cropper = imageCropper(),
            fromCamera = {},
            fromGallery = {},
        ),
        systemName = "???? System",
        loading = loading,
        showAvatarSheet = false,
        showCoverSheet = false,
        showCamera = true,
    ) {}
