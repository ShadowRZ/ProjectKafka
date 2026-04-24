package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.attafitamim.krop.core.crop.imageCropper
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState

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
        avatar = "",
        cover = "",
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
        avatar = "dummy",
        cover = "dummy",
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
