package io.github.shadowrz.projectkafka.features.editmember.impl

import kotlinx.datetime.LocalDate

sealed interface AddMemberEvents {
    data object Back : AddMemberEvents

    data object Save : AddMemberEvents

    data object CloseDirtyDialog : AddMemberEvents

    data object DiscardChanges : AddMemberEvents

    data class ChangeBirth(
        val birth: LocalDate?,
    ) : AddMemberEvents

    data class ChangeAdmin(
        val admin: Boolean,
    ) : AddMemberEvents
}
