package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.material3.SnackbarHostState
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

data class DataManageState(
    val snackbarHostState: SnackbarHostState,
    val eventSink: (DataManageEvents) -> Unit,
) : HanekokoroState
