package io.github.shadowrz.projectkafka.features.ftue.api

import kotlinx.coroutines.flow.StateFlow

interface FtueService {
    val state: StateFlow<FtueState>
}
