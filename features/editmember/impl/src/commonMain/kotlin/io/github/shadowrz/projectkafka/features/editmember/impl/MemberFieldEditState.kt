package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperState
import kotlinx.datetime.LocalDate

@Stable
data class MemberFieldEditState(
    val name: TextFieldState,
    val description: TextFieldState,
    val avatar: CropperState,
    val cover: Uri?,
    val preferences: TextFieldState,
    val roles: TextFieldState,
    val birth: LocalDate?,
    val admin: Boolean,
    val valid: Boolean,
    val dirty: Boolean,
    val showDirtyDialog: Boolean,
    val saving: Boolean,
    val eventSink: (MemberFieldEditEvents) -> Unit,
) {
    @Immutable
    data class FieldState(
        val name: String = "",
        val description: String = "",
        val avatar: Uri = Uri.Companion.EMPTY,
        val cover: Uri = Uri.Companion.EMPTY,
        val preferences: String = "",
        val roles: String = "",
        val birth: LocalDate? = null,
        val admin: Boolean = false,
    )
}
