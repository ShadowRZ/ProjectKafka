package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta
import io.github.shadowrz.projectkafka.libraries.core.extensions.toNullableString
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperProvider
import io.github.shadowrz.projectkafka.libraries.data.api.MediaFile
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
    private val buildMeta: BuildMeta,
) : Presenter<AddDetailsState> {
    @Composable
    override fun present(): AddDetailsState {
        var avatar by rememberSaveable { mutableStateOf("") }
        var cover by rememberSaveable { mutableStateOf("") }
        val avatarCropper = cropperProvider.rememberCropperState {
            avatar = it.toString()
        }
        val coverCropper = cropperProvider.rememberCropperState {
            cover = it.toString()
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
            showCamera = buildMeta.platform != BuildMeta.Platform.Desktop,
        ) {
            when (it) {
                AddDetailsEvents.CreateSystem -> {
                    appCoroutineScope.launch {
                        val id =
                            systemsStore.createSystem(
                                name = systemName,
                                description = null,
                                avatar = avatar.toNullableString()?.let { MediaFile(it) },
                                cover = cover.toNullableString()?.let { MediaFile(it) },
                            )
                        callback.onFinish(id)
                    }
                }

                AddDetailsEvents.ClearAvatar -> {
                    avatar = ""
                }

                AddDetailsEvents.ClearCover -> {
                    cover = ""
                }

                AddDetailsEvents.OpenAvatarPickerSheet -> {
                    when (buildMeta.platform) {
                        BuildMeta.Platform.Desktop if avatar == "" -> avatarCropper.fromGallery()
                        else -> showAvatarSheet = true
                    }
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
