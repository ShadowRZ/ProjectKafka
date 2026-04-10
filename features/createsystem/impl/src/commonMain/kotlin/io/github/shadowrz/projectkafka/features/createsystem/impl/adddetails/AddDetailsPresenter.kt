package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.eygraber.uri.toKmpUri
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
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
        var avatarStr by rememberSaveable { mutableStateOf("") }
        var coverStr by rememberSaveable { mutableStateOf("") }
        val avatar by remember { derivedStateOf { avatarStr.toKmpUri() } }
        val cover by remember { derivedStateOf { coverStr.toKmpUri() } }
        val avatarCropper = cropperProvider.rememberCropperState {
            avatarStr = it.toString()
        }
        val coverCropper = cropperProvider.rememberCropperState {
            coverStr = it.toString()
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
                AddDetailsEvents.CreateSystem -> {
                    appCoroutineScope.launch {
                        val id =
                            systemsStore.createSystem(
                                name = systemName,
                                description = null,
                                avatar = avatar,
                                cover = cover,
                            )
                        callback.onFinish(id)
                    }
                }

                AddDetailsEvents.ClearAvatar -> {
                    avatarStr = ""
                }

                AddDetailsEvents.ClearCover -> {
                    coverStr = ""
                }

                AddDetailsEvents.OpenAvatarPickerSheet -> {
                    showAvatarSheet = true
                }

                AddDetailsEvents.DismissAvatarPickerSheet -> {
                    showAvatarSheet = false
                }

                AddDetailsEvents.OpenCoverPickerSheet -> {
                    showCoverSheet = true
                }

                AddDetailsEvents.DismissCoverPickerSheet -> {
                    showCoverSheet = false
                }

                AddDetailsEvents.SelectAvatarFromCamera -> {
                    showAvatarSheet = false
                    avatarCropper.fromCamera()
                }

                AddDetailsEvents.SelectAvatarFromGallery -> {
                    showAvatarSheet = false
                    avatarCropper.fromGallery()
                }

                AddDetailsEvents.SelectCoverFromCamera -> {
                    showCoverSheet = false
                    coverCropper.fromCamera()
                }

                AddDetailsEvents.SelectCoverFromGallery -> {
                    showCoverSheet = false
                    coverCropper.fromGallery()
                }
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
