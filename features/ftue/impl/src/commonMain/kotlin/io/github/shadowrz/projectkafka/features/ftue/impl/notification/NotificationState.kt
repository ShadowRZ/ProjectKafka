package io.github.shadowrz.projectkafka.features.ftue.impl.notification

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

@Stable
data class NotificationState(
    val eventSink: (NotificationEvents) -> Unit,
) : HanekokoroState
