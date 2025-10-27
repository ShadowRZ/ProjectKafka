package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperState

class AddDetailsStateProvider : PreviewParameterProvider<AddDetailsState> {
    override val values: Sequence<AddDetailsState>
        get() =
            sequenceOf(
                aAddDetailsState(),
                aAddDetailsState(loading = true),
            )
}

private fun aAddDetailsState(loading: Boolean = false) =
    AddDetailsState(
        coverState = CropperState(),
        avatarState = CropperState(),
        systemName = "???? System",
        loading = loading,
    ) {}
