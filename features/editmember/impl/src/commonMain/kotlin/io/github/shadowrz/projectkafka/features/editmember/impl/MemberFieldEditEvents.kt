package io.github.shadowrz.projectkafka.features.editmember.impl

import kotlinx.datetime.LocalDate

sealed interface MemberFieldEditEvents {
    data object Back : MemberFieldEditEvents

    data object Save : MemberFieldEditEvents

    data object CloseDirtyDialog : MemberFieldEditEvents

    data object DiscardChanges : MemberFieldEditEvents

    data object SelectAvatarFromCamera : MemberFieldEditEvents

    data object SelectAvatarFromGallery : MemberFieldEditEvents

    data object ClearAvatar : MemberFieldEditEvents

    data object OpenAvatarPickerSheet : MemberFieldEditEvents

    data object DismissAvatarPickerSheet : MemberFieldEditEvents

    data object SelectCoverFromCamera : MemberFieldEditEvents

    data object SelectCoverFromGallery : MemberFieldEditEvents

    data object ClearCover : MemberFieldEditEvents

    data object OpenCoverPickerSheet : MemberFieldEditEvents

    data object DismissCoverPickerSheet : MemberFieldEditEvents

    data class ChangeBirth(val birth: LocalDate?) : MemberFieldEditEvents

    data class ChangeAdmin(val admin: Boolean) : MemberFieldEditEvents
}
