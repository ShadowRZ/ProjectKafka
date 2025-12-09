package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.runtime.Stable
import com.composables.core.DialogState
import io.github.shadowrz.projectkafka.libraries.data.api.System

@Stable
data class HomeState(
    val system: System,
    val showingDialog: ShowingDialog,
    val dialogState: DialogState,
    val allowsMultiSystem: Boolean,
    val eventSink: (HomeEvents) -> Unit,
) {
    enum class ShowingDialog {
        Closed,
        SystemMenu,
        Help,
    }
}
