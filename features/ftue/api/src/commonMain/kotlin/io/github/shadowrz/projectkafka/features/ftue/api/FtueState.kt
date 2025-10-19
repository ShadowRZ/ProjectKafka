package io.github.shadowrz.projectkafka.features.ftue.api

sealed interface FtueState {
    data object Unknown : FtueState

    data object Incomplete : FtueState

    data object Complete : FtueState
}
