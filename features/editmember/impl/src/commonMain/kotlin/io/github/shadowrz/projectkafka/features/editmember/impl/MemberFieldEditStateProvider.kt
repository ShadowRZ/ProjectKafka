package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperState
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
    saving: Boolean = false,
) = MemberFieldEditState(
    name = TextFieldState(initialText = name),
    description = TextFieldState(initialText = description.orEmpty()),
    avatar = CropperState(value = avatar ?: Uri.EMPTY),
    cover = cover,
    preferences = TextFieldState(initialText = preferences.orEmpty()),
    roles = TextFieldState(initialText = roles.orEmpty()),
    birth = birth,
    admin = admin,
    valid = valid,
    dirty = dirty,
    showDirtyDialog = showDirtyDialog,
    saving = saving,
) {
}
