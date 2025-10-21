package io.github.shadowrz.projectkafka.features.datamanage.impl

sealed interface DataManageEvents {
    data object Backup : DataManageEvents

    data object Restore : DataManageEvents
}
