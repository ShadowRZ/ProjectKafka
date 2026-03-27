package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.kafkaui.AvatarPickerState
import io.github.shadowrz.projectkafka.libraries.kafkaui.CoverPickerState

@AssistedInject
class MemberFieldEditPresenter(
    @Assisted private val initialState: MemberFieldEditState.FieldState,
    @Assisted private val callback: MemberFieldEditCallback,
    private val cropperProvider: CropperProvider,
) : Presenter<MemberFieldEditState> {
    @Composable
    override fun present(): MemberFieldEditState {
        var avatar by rememberSerializable { mutableStateOf(initialState.avatar) }
        val avatarCropper = cropperProvider.rememberCropperState {
            avatar = AvatarPickerState.Selected(it)
        }
        val name = rememberTextFieldState(initialText = initialState.name)
        val description = rememberTextFieldState(initialText = initialState.description)
        val preferences = rememberTextFieldState(initialText = initialState.preferences)
        val roles = rememberTextFieldState(initialText = initialState.roles)
        var birth by rememberSaveable { mutableStateOf(initialState.birth) }
        var admin by rememberSaveable { mutableStateOf(initialState.admin) }
        val valid by remember {
            derivedStateOf {
                !name.text.isEmpty()
            }
        }
        var showDirtyDialog by rememberSaveable { mutableStateOf(false) }
        var saving by rememberSaveable { mutableStateOf(false) }
        var showAvatarSheet by rememberSaveable { mutableStateOf(false) }

        val currentState =
            MemberFieldEditState.FieldState(
                name = name.text.toString(),
                description = description.text.toString(),
                avatar = avatar,
                preferences = preferences.text.toString(),
                roles = roles.text.toString(),
                birth = birth,
                admin = admin,
            )

        val dirty = currentState != initialState

        return MemberFieldEditState(
            name = name,
            description = description,
            avatar = avatar,
            avatarCropper = avatarCropper,
            coverCropper = avatarCropper,
            cover = CoverPickerState.Pick,
            preferences = preferences,
            roles = roles,
            birth = birth,
            admin = admin,
            valid = valid,
            dirty = dirty,
            saving = saving,
            showDirtyDialog = showDirtyDialog,
            showAvatarSheet = showAvatarSheet,
        ) {
            when (it) {
                MemberFieldEditEvents.Back -> {
                    if (dirty) {
                        showDirtyDialog = true
                    } else {
                        callback.onBack()
                    }
                }

                MemberFieldEditEvents.CloseDirtyDialog -> {
                    showDirtyDialog = false
                }

                MemberFieldEditEvents.DiscardChanges -> {
                    showDirtyDialog = false
                    callback.onBack()
                }

                is MemberFieldEditEvents.ChangeBirth -> {
                    birth = it.birth
                }

                is MemberFieldEditEvents.ChangeAdmin -> {
                    admin = it.admin
                }

                MemberFieldEditEvents.Save -> {
                    if (valid && dirty) {
                        saving = true
                        callback.onSave(currentState)
                        saving = false
                    }
                }

                MemberFieldEditEvents.ClearAvatar -> {
                    avatar = AvatarPickerState.Pick
                }

                MemberFieldEditEvents.OpenAvatarPickerSheet -> {
                    showAvatarSheet = true
                }

                MemberFieldEditEvents.DismissAvatarPickerSheet -> {
                    showAvatarSheet = false
                }

                MemberFieldEditEvents.SelectAvatarFromCamera -> {
                    avatarCropper.fromCamera()
                }

                MemberFieldEditEvents.SelectAvatarFromGallery -> {
                    avatarCropper.fromGallery()
                }
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(
            initialState: MemberFieldEditState.FieldState,
            callback: MemberFieldEditCallback,
        ): MemberFieldEditPresenter
    }
}
