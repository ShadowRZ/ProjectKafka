package io.github.shadowrz.projectkafka.libraries.resultevents

import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

sealed interface ResultEvents {
    data class SystemCreated(val id: SystemID) : ResultEvents

    data class MemberDeleted(val id: MemberID) : ResultEvents

    data class SwitchSystem(val id: SystemID) : ResultEvents
}
