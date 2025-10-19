package io.github.shadowrz.projectkafka.features.ftue.impl.notification

sealed interface NotificationEvents {
    data object SkipNotification : NotificationEvents

    data object RequestNotification : NotificationEvents
}
