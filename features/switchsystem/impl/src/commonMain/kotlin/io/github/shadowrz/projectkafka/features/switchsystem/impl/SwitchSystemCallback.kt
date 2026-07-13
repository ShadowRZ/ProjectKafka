package io.github.shadowrz.projectkafka.features.switchsystem.impl

import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

interface SwitchSystemCallback {
    fun onCreateSystem()

    fun onSwitchSystem(id: SystemID)
}
