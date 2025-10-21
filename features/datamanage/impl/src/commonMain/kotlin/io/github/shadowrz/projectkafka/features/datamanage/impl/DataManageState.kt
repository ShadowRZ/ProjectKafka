package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.material3.SnackbarHostState

data class DataManageState(
    val snackbarHostState: SnackbarHostState,
    val eventSink: (DataManageEvents) -> Unit,
)
