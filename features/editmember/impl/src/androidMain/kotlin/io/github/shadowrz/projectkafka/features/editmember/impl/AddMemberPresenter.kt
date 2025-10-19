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
import com.eygraber.uri.Uri
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectProfileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@AssistedInject
actual class AddMemberPresenter(
    @Assisted private val callback: AddMemberComponent.Callback,
    @Assisted private val imageCropper: ImageCropper,
    private val pickerProvider: PickerProvider,
    private val selectProfileProvider: SelectProfileProvider,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
    private val membersStore: MembersStore,
) : Presenter<AddMemberState> {
    @Suppress("detekt:CyclomaticComplexMethod")
    @Composable
    override fun present(): AddMemberState {
        val avatar =
            selectProfileProvider.rememberSelectAvatarState(
                pickerProvider = pickerProvider,
                scope = systemCoroutineScope,
                imageCropper = imageCropper,
            )
        val name = rememberTextFieldState()
        val description = rememberTextFieldState()
        val preferences = rememberTextFieldState()
        val roles = rememberTextFieldState()
        val valid by remember {
            derivedStateOf {
                !name.text.isEmpty()
            }
        }
        var birth by rememberSaveable { mutableStateOf<LocalDate?>(null) }
        var admin by rememberSaveable { mutableStateOf(false) }
        var showDirtyDialog by rememberSaveable { mutableStateOf(false) }
        var saving by rememberSaveable { mutableStateOf(false) }

        val dirty =
            avatar.value != Uri.EMPTY ||
                !name.text.isEmpty() ||
                !description.text.isEmpty() ||
                !preferences.text.isEmpty() ||
                !roles.text.isEmpty() ||
                birth != null ||
                admin

        return AddMemberState(
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
                AddMemberEvents.Back -> {
                    if (dirty) {
                        showDirtyDialog = true
                    } else {
                        callback.onFinish()
                    }
                }

                AddMemberEvents.CloseDirtyDialog ->
                    showDirtyDialog =
                        false

                AddMemberEvents.DiscardChanges -> {
                    showDirtyDialog = false
                    callback.onFinish()
                }

                is AddMemberEvents.ChangeBirth ->
                    birth =
                        it.birth

                is AddMemberEvents.ChangeAdmin ->
                    admin =
                        it.admin

                AddMemberEvents.Save -> {
                    if (valid) {
                        saving = true
                        systemCoroutineScope.launch {
                            membersStore.createMember(
                                name = name.text.toString(),
                                description = description.text.toString(),
                                avatar = avatar.value,
                                cover = null,
                                preferences = preferences.text.toString(),
                                roles = roles.text.toString(),
                                birth = birth,
                                admin = admin,
                            )
                            saving = false
                            callback.onFinish()
                        }
                    }
                }
            }
        }
    }

    @AssistedFactory
    actual fun interface Factory {
        actual fun create(
            callback: AddMemberComponent.Callback,
            imageCropper: ImageCropper,
        ): AddMemberPresenter
    }
}
