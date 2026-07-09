package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.System

@Stable
data class HomeState(
    val system: System,
    val showingDialog: ShowingDialog,
    val allowsMultiSystem: Boolean,
    val members: AsyncOutcome<List<Member>>,
    val eventSink: (HomeEvents) -> Unit,
) : HanekokoroState {
    enum class ShowingDialog {
        Closed,
        SystemMenu,
        Help,
        NewChatCreator,
    }
}
