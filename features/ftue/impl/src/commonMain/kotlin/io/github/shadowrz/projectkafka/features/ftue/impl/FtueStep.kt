package io.github.shadowrz.projectkafka.features.ftue.impl

sealed interface FtueStep {
    data object NotificationOptIn : FtueStep
}
