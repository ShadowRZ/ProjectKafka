package io.github.shadowrz.projectkafka.libraries.preferences.api

import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.coroutines.flow.Flow

interface AppPreferencesStore {
    fun dynamicColor(): Flow<Boolean>

    suspend fun setDynamicColor(dynamicColor: Boolean)

    fun currentSystem(): Flow<SystemID?>

    suspend fun setCurrentSystem(id: SystemID)
}
