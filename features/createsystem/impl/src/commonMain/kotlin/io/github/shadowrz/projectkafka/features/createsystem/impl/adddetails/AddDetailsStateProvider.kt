package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectImageState

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
        coverState = SelectImageState(),
        avatarState = SelectImageState(),
        systemName = "???? System",
        loading = loading,
    ) {}
