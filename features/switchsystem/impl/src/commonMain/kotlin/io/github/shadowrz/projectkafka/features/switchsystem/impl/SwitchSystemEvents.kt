package io.github.shadowrz.projectkafka.features.switchsystem.impl

import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

sealed interface SwitchSystemEvents {
    data class SwitchSystem(
        val id: SystemID,
    ) : SwitchSystemEvents

    data object CreateSystem : SwitchSystemEvents
}
