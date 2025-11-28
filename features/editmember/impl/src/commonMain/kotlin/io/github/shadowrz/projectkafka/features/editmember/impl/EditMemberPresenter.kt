package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.attafitamim.krop.core.crop.ImageCropper
import com.eygraber.uri.Uri
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.runtime.Presenter
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.core.extensions.toNullableString
import io.github.shadowrz.projectkafka.libraries.core.extensions.toNullableUri
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@AssistedInject
class EditMemberPresenter(
    @Assisted private val memberID: MemberID,
    @Assisted private val callback: EditMemberComponent.Callback,
    @Assisted private val imageCropper: ImageCropper,
    private val presenterFactory: MemberFieldEditPresenter.Factory,
    private val membersStore: MembersStore,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
) : Presenter<Result<MemberFieldEditState>> {
    private val editCallback = object : MemberFieldEditCallback {
        override fun onBack() {
            callback.onFinish()
        }

        override fun onSave(state: MemberFieldEditState.FieldState) {
            systemCoroutineScope.launch {
                membersStore.updateMember(
                    id = memberID,
                    name = state.name,
                    description = state.description.toNullableString(),
                    avatar = state.avatar.toNullableUri(),
                    cover = state.cover.toNullableUri(),
                    preferences = state.preferences.toNullableString(),
                    roles = state.roles.toNullableString(),
                    birth = state.birth,
                    admin = state.admin,
                )
                callback.onFinish()
            }
        }
    }

    private val initialState = membersStore
        .getMember(memberID)
        .map { member ->
            member?.let {
                Result.Success(
                    MemberFieldEditState.FieldState(
                        name = it.name,
                        description = it.description.orEmpty(),
                        avatar = it.avatar ?: Uri.EMPTY,
                        cover = it.cover ?: Uri.EMPTY,
                        preferences = it.preferences.orEmpty(),
                        roles = it.roles.orEmpty(),
                        birth = it.birth,
                        admin = it.admin,
                    ),
                )
            } ?: Result.Loading
        }.stateIn(
            scope = systemCoroutineScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Result.Loading,
        )

    @Composable
    override fun present(): Result<MemberFieldEditState> {
        val initialState by initialState.collectAsStateWithLifecycle()

        return when (initialState) {
            Result.Loading -> {
                Result.Loading
            }

            is Result.Success<MemberFieldEditState.FieldState> -> {
                val state = presenterFactory
                    .create(
                        (initialState as Result.Success<MemberFieldEditState.FieldState>).value,
                        imageCropper = imageCropper,
                        callback = editCallback,
                    ).present()

                Result.Success(state)
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(
            memberID: MemberID,
            callback: EditMemberComponent.Callback,
            imageCropper: ImageCropper,
        ): EditMemberPresenter
    }
}
