package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState
import io.github.shadowrz.projectkafka.libraries.data.api.MediaFile
import kotlinx.datetime.LocalDate

@Stable
data class MemberFieldEditState(
    val name: TextFieldState,
    val description: TextFieldState,
    val avatar: String,
    val avatarCropper: CropperState,
    val cover: String,
    val coverCropper: CropperState,
    val preferences: TextFieldState,
    val roles: TextFieldState,
    val birth: LocalDate?,
    val admin: Boolean,
    val valid: Boolean,
    val dirty: Boolean,
    val showDirtyDialog: Boolean,
    val showAvatarSheet: Boolean,
    val showCamera: Boolean,
    val saving: Boolean,
    val eventSink: (MemberFieldEditEvents) -> Unit,
) : HanekokoroState {
    @Immutable
    data class FieldState(
        val name: String = "",
        val description: String = "",
        val avatar: MediaFile? = null,
        val cover: MediaFile? = null,
        val preferences: String = "",
        val roles: String = "",
        val birth: LocalDate? = null,
        val admin: Boolean = false,
    ) : HanekokoroState
}
