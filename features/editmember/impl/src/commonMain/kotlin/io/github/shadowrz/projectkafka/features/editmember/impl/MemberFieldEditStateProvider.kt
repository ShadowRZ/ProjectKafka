package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.attafitamim.krop.core.crop.imageCropper
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState
import io.github.shadowrz.projectkafka.libraries.kafkaui.AvatarPickerState
import io.github.shadowrz.projectkafka.libraries.kafkaui.CoverPickerState
import kotlinx.datetime.LocalDate

class MemberFieldEditStateProvider : PreviewParameterProvider<MemberFieldEditState> {
    override val values: Sequence<MemberFieldEditState>
        get() =
            sequenceOf(
                aMemberState(),
                aMemberState(valid = false),
                aMemberState(name = "????"),
                aMemberState(
                    name = "????",
                    description = "[Unknown]",
                ),
                aMemberState(
                    name = "????",
                    description = "[Unknown]",
                    preferences = "(Preferences)",
                ),
                aMemberState(
                    name = "????",
                    description = "[Unknown]",
                    preferences = "(Preferences)",
                    roles = "(Roles)",
                ),
                aMemberState(
                    name = "????",
                    description = "[Unknown]",
                    preferences = "(Preferences)",
                    roles = "(Roles)",
                    birth = LocalDate(2024, 1, 1),
                ),
                aMemberState(
                    name = "????",
                    description = "[Unknown]",
                    preferences = "(Preferences)",
                    roles = "(Roles)",
                    birth = LocalDate(2024, 1, 1),
                    admin = true,
                ),
                aMemberState(
                    name = "????",
                    description = "[Unknown]",
                    preferences = "(Preferences)",
                    roles = "(Roles)",
                    birth = LocalDate(2024, 1, 1),
                    admin = true,
                    saving = true,
                ),
                aMemberState(
                    name = "????",
                    description = "[Unknown]",
                    preferences = "(Preferences)",
                    roles = "(Roles)",
                    birth = LocalDate(2024, 1, 1),
                    admin = true,
                    showAvatarSheet = true,
                ),
                aMemberState(
                    name = "????",
                    description = "[Unknown]",
                    preferences = "(Preferences)",
                    roles = "(Roles)",
                    birth = LocalDate(2024, 1, 1),
                    admin = true,
                    showDirtyDialog = true,
                ),
            )
}

private fun aMemberState(
    name: String = "",
    description: String? = null,
    avatar: Uri? = null,
    cover: Uri? = null,
    preferences: String? = null,
    roles: String? = null,
    birth: LocalDate? = null,
    admin: Boolean = false,
    valid: Boolean = true,
    dirty: Boolean = false,
    showDirtyDialog: Boolean = false,
    showAvatarSheet: Boolean = true,
    saving: Boolean = false,
) = MemberFieldEditState(
    name = TextFieldState(initialText = name),
    description = TextFieldState(initialText = description.orEmpty()),
    avatar = avatar?.let { avatar ->
        AvatarPickerState.Selected(avatar)
    } ?: AvatarPickerState.Pick,
    avatarCropper = CropperState(
        cropper = imageCropper(),
        fromCamera = {},
        fromGallery = {},
    ),
    coverCropper = CropperState(
        cropper = imageCropper(),
        fromCamera = {},
        fromGallery = {},
    ),
    cover = cover?.let { cover ->
        CoverPickerState.Selected(cover)
    } ?: CoverPickerState.Pick,
    preferences = TextFieldState(initialText = preferences.orEmpty()),
    roles = TextFieldState(initialText = roles.orEmpty()),
    birth = birth,
    admin = admin,
    valid = valid,
    dirty = dirty,
    showDirtyDialog = showDirtyDialog,
    showAvatarSheet = showAvatarSheet,
    saving = saving,
) {
}
