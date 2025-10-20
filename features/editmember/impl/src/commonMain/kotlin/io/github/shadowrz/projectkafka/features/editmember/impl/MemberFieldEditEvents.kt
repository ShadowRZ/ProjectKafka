package io.github.shadowrz.projectkafka.features.editmember.impl

import kotlinx.datetime.LocalDate

sealed interface MemberFieldEditEvents {
    data object Back : MemberFieldEditEvents

    data object Save : MemberFieldEditEvents

    data object CloseDirtyDialog : MemberFieldEditEvents

    data object DiscardChanges : MemberFieldEditEvents

    data class ChangeBirth(
        val birth: LocalDate?,
    ) : MemberFieldEditEvents

    data class ChangeAdmin(
        val admin: Boolean,
    ) : MemberFieldEditEvents
}
