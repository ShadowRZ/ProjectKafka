package io.github.shadowrz.projectkafka.libraries.preferences.api

import kotlinx.coroutines.flow.Flow

interface AppPreferencesStore {
    fun dynamicColor(): Flow<Boolean>

    suspend fun setDynamicColor(dynamicColor: Boolean)

    fun allowsMultiSystem(): Flow<Boolean>

    suspend fun setAllowsMultiSystem(allowsMultiSystem: Boolean)
}
