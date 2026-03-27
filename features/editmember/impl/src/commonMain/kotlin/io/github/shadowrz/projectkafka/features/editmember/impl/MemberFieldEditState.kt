package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState
import io.github.shadowrz.projectkafka.libraries.kafkaui.AvatarPickerState
import io.github.shadowrz.projectkafka.libraries.kafkaui.CoverPickerState
import kotlinx.datetime.LocalDate

@Stable
data class MemberFieldEditState(
    val name: TextFieldState,
    val description: TextFieldState,
    val avatar: AvatarPickerState,
    val avatarCropper: CropperState,
    val cover: CoverPickerState,
    val coverCropper: CropperState,
    val preferences: TextFieldState,
    val roles: TextFieldState,
    val birth: LocalDate?,
    val admin: Boolean,
    val valid: Boolean,
    val dirty: Boolean,
    val showDirtyDialog: Boolean,
    val showAvatarSheet: Boolean,
    val saving: Boolean,
    val eventSink: (MemberFieldEditEvents) -> Unit,
) : HanekokoroState {
    @Immutable
    data class FieldState(
        val name: String = "",
        val description: String = "",
        val avatar: AvatarPickerState = AvatarPickerState.Pick,
        val cover: CoverPickerState = CoverPickerState.Pick,
        val preferences: String = "",
        val roles: String = "",
        val birth: LocalDate? = null,
        val admin: Boolean = false,
    ) : HanekokoroState
}
