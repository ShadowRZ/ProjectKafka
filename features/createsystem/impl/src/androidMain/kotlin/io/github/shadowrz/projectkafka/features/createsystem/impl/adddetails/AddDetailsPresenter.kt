package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
actual class AddDetailsPresenter(
    @Assisted private val systemName: String,
    @Assisted private val callback: AddDetailsComponent.Callback,
    @Assisted private val croppers: AddDetailsComponent.Croppers,
    private val cropperProvider: CropperProvider,
    private val appCoroutineScope: CoroutineScope,
    private val systemsStore: SystemsStore,
) : Presenter<AddDetailsState> {
    @Composable
    override fun present(): AddDetailsState {
        val coverState =
            cropperProvider.rememberCropperState(
                imageCropper = croppers.cover,
            )
        val avatarState =
            cropperProvider.rememberCropperState(
                imageCropper = croppers.avatar,
            )
        var loading by remember { mutableStateOf(false) }

        return AddDetailsState(
            coverState = coverState,
            avatarState = avatarState,
            systemName = systemName,
            loading = loading,
        ) {
            appCoroutineScope.launch {
                val id =
                    systemsStore.createSystem(
                        name = systemName,
                        description = null,
                        avatar = avatarState.value,
                        cover = coverState.value,
                    )
                callback.onFinish(id)
            }
        }
    }

    @AssistedFactory
    actual fun interface Factory {
        actual fun create(
            systemName: String,
            callback: AddDetailsComponent.Callback,
            croppers: AddDetailsComponent.Croppers,
        ): AddDetailsPresenter
    }
}
