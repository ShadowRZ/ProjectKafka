package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.data.api.System

@Stable
data class HomeState(
    val system: System,
    val showingDialog: ShowingDialog,
    val dialogVisible: Boolean,
    val allowsMultiSystem: Boolean,
    val eventSink: (HomeEvents) -> Unit,
) : HanekokoroState {
    enum class ShowingDialog {
        Closed,
        SystemMenu,
        Help,
    }
}
