package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.runtime.Composable
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
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import io.github.shadowrz.projectkafka.libraries.kafkaui.AvatarPickerState
import io.github.shadowrz.projectkafka.libraries.kafkaui.CoverPickerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class AddDetailsPresenter(
    @Assisted private val systemName: String,
    @Assisted private val callback: AddDetailsComponent.Callback,
    private val cropperProvider: CropperProvider,
    private val appCoroutineScope: CoroutineScope,
    private val systemsStore: SystemsStore,
) : Presenter<AddDetailsState> {
    @Composable
    override fun present(): AddDetailsState {
        var avatar by rememberSerializable<AvatarPickerState> {
            mutableStateOf(AvatarPickerState.Pick)
        }
        var cover by rememberSerializable<CoverPickerState> {
            mutableStateOf(CoverPickerState.Pick)
        }
        val avatarCropper = cropperProvider.rememberCropperState {
            avatar = AvatarPickerState.Selected(it)
        }
        val coverCropper = cropperProvider.rememberCropperState {
            cover = CoverPickerState.Selected(it)
        }
        var loading by remember { mutableStateOf(false) }

        var showAvatarSheet by rememberSaveable { mutableStateOf(false) }
        var showCoverSheet by rememberSaveable { mutableStateOf(false) }

        return AddDetailsState(
            avatar = avatar,
            cover = cover,
            avatarCropper = avatarCropper,
            coverCropper = coverCropper,
            systemName = systemName,
            loading = loading,
            showAvatarSheet = showAvatarSheet,
            showCoverSheet = showCoverSheet,
        ) {
            when (it) {
                AddDetailsEvents.CreateSystem -> appCoroutineScope.launch {
                    val id =
                        systemsStore.createSystem(
                            name = systemName,
                            description = null,
                            avatar = when (val state = avatar) {
                                AvatarPickerState.Pick -> null
                                is AvatarPickerState.Selected -> state.value
                            },
                            cover = when (val state = cover) {
                                CoverPickerState.Pick -> null
                                is CoverPickerState.Selected -> state.value
                            },
                        )
                    callback.onFinish(id)
                }

                AddDetailsEvents.ClearAvatar -> avatar = AvatarPickerState.Pick

                AddDetailsEvents.ClearCover -> cover = CoverPickerState.Pick

                AddDetailsEvents.OpenAvatarPickerSheet -> showAvatarSheet = true

                AddDetailsEvents.DismissAvatarPickerSheet -> showAvatarSheet = false

                AddDetailsEvents.OpenCoverPickerSheet -> showCoverSheet = true

                AddDetailsEvents.DismissCoverPickerSheet -> showCoverSheet = false

                AddDetailsEvents.SelectAvatarFromCamera -> avatarCropper.fromCamera()

                AddDetailsEvents.SelectAvatarFromGallery -> avatarCropper.fromGallery()

                AddDetailsEvents.SelectCoverFromCamera -> coverCropper.fromCamera()

                AddDetailsEvents.SelectCoverFromGallery -> coverCropper.fromGallery()
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(
            systemName: String,
            callback: AddDetailsComponent.Callback,
        ): AddDetailsPresenter
    }
}
