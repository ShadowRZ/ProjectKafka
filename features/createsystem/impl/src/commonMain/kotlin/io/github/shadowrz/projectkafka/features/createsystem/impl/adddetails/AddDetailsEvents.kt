package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

sealed interface AddDetailsEvents {
    data object CreateSystem : AddDetailsEvents

    data object SelectAvatarFromCamera : AddDetailsEvents

    data object SelectAvatarFromGallery : AddDetailsEvents

    data object ClearAvatar : AddDetailsEvents

    data object OpenAvatarPickerSheet : AddDetailsEvents

    data object DismissAvatarPickerSheet : AddDetailsEvents

    data object SelectCoverFromCamera : AddDetailsEvents

    data object SelectCoverFromGallery : AddDetailsEvents

    data object ClearCover : AddDetailsEvents

    data object OpenCoverPickerSheet : AddDetailsEvents

    data object DismissCoverPickerSheet : AddDetailsEvents
}
