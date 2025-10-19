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
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerProvider
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectProfileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

expect class AddDetailsPresenter : Presenter<AddDetailsState> {
    fun interface Factory {
        fun create(
            systemName: String,
            callback: AddDetailsComponent.Callback,
            croppers: AddDetailsComponent.Croppers,
        ): AddDetailsPresenter
    }
}
