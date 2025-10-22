package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.attafitamim.krop.core.crop.ImageCropper
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectProfileProvider
import kotlinx.coroutines.CoroutineScope

@AssistedInject
actual class MemberFieldEditPresenter(
    @Assisted private val imageCropper: ImageCropper,
    @Assisted private val initialState: MemberFieldEditState.FieldState,
    @Assisted private val callback: MemberFieldEditCallback,
    private val pickerProvider: PickerProvider,
    private val selectProfileProvider: SelectProfileProvider,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
) : Presenter<MemberFieldEditState> {
    @Composable
    override fun present(): MemberFieldEditState {
        val avatar =
            selectProfileProvider.rememberSelectAvatarState(
                pickerProvider = pickerProvider,
                scope = systemCoroutineScope,
                imageCropper = imageCropper,
                initialValue = initialState.avatar,
            )
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

        val currentState =
            MemberFieldEditState.FieldState(
                name = name.text.toString(),
                description = description.text.toString(),
                avatar = avatar.value,
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
            cover = null,
            preferences = preferences,
            roles = roles,
            birth = birth,
            admin = admin,
            valid = valid,
            dirty = dirty,
            showDirtyDialog = showDirtyDialog,
            saving = saving,
        ) {
            when (it) {
                MemberFieldEditEvents.Back -> {
                    if (dirty) {
                        showDirtyDialog = true
                    } else {
                        callback.onBack()
                    }
                }
                MemberFieldEditEvents.CloseDirtyDialog -> showDirtyDialog = false
                MemberFieldEditEvents.DiscardChanges -> {
                    showDirtyDialog = false
                    callback.onBack()
                }
                is MemberFieldEditEvents.ChangeBirth -> birth = it.birth
                is MemberFieldEditEvents.ChangeAdmin -> admin = it.admin
                MemberFieldEditEvents.Save -> {
                    if (valid && dirty) {
                        saving = true
                        callback.onSave(currentState)
                        saving = false
                    }
                }
            }
        }
    }

    @AssistedFactory
    actual fun interface Factory {
        actual fun create(
            initialState: MemberFieldEditState.FieldState,
            imageCropper: ImageCropper,
            callback: MemberFieldEditCallback,
        ): MemberFieldEditPresenter
    }
}
