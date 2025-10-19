package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectImageState
import kotlinx.datetime.LocalDate

@Stable
data class AddMemberState(
    val name: TextFieldState,
    val description: TextFieldState,
    val avatar: SelectImageState,
    val cover: Uri?,
    val preferences: TextFieldState,
    val roles: TextFieldState,
    val birth: LocalDate?,
    val admin: Boolean,
    val valid: Boolean,
    val dirty: Boolean,
    val showDirtyDialog: Boolean,
    val saving: Boolean,
    val eventSink: (AddMemberEvents) -> Unit,
)
