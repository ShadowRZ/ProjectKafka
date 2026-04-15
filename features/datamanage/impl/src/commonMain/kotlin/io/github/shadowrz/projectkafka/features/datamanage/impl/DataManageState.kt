package io.github.shadowrz.projectkafka.features.datamanage.impl

import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

data class DataManageState(
    val eventSink: (DataManageEvents) -> Unit,
) : HanekokoroState
