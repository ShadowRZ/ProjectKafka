package io.github.shadowrz.projectkafka.features.ftue.impl.notification

import androidx.compose.runtime.Stable

@Stable
data class NotificationState(
    val eventSink: (NotificationEvents) -> Unit,
)
