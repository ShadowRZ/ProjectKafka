package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.runtime.Composable
import com.attafitamim.krop.core.crop.ImageCropper
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.core.extensions.toNullableString
import io.github.shadowrz.projectkafka.libraries.core.extensions.toNullableUri
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class AddMemberPresenter(
    @Assisted private val callback: AddMemberComponent.Callback,
    @Assisted private val imageCropper: ImageCropper,
    presenterFactory: MemberFieldEditPresenter.Factory,
    private val membersStore: MembersStore,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
) : Presenter<MemberFieldEditState> {
    private val presenter = presenterFactory.create(
        initialState = MemberFieldEditState.FieldState(),
        imageCropper = imageCropper,
        callback = object : MemberFieldEditCallback {
            override fun onBack() {
                callback.onFinish()
            }

            override fun onSave(state: MemberFieldEditState.FieldState) {
                systemCoroutineScope.launch {
                    membersStore.createMember(
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
        },
    )

    @Composable
    override fun present(): MemberFieldEditState = presenter.present()

    @AssistedFactory
    fun interface Factory {
        fun create(
            callback: AddMemberComponent.Callback,
            imageCropper: ImageCropper,
        ): AddMemberPresenter
    }
}
