package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

sealed interface AddDetailsEvents {
    data object CreateSystem : AddDetailsEvents

    data object SelectAvatarFromCamera : AddDetailsEvents

    data object SelectAvatarFromGallery : AddDetailsEvents

    data object ClearAvatar : AddDetailsEvents

    data class ChangeAvatarSheetState(
        val state: Boolean,
    ) : AddDetailsEvents

    data class ChangeCoverSheetState(
        val state: Boolean,
    ) : AddDetailsEvents

    data object SelectCoverFromCamera : AddDetailsEvents

    data object SelectCoverFromGallery : AddDetailsEvents

    data object ClearCover : AddDetailsEvents
}
