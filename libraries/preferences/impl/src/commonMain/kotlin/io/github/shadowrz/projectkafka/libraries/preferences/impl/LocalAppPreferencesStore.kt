package io.github.shadowrz.projectkafka.libraries.preferences.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import io.github.shadowrz.projectkafka.libraries.preferences.api.AppPreferencesStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class LocalAppPreferencesStore : AppPreferencesStore {
    abstract val store: DataStore<Preferences>

    override fun dynamicColor(): Flow<Boolean> =
        store.data.map { preferences ->
            preferences[DYNAMIC_COLOR_KEY] != false
        }

    override suspend fun setDynamicColor(dynamicColor: Boolean) {
        store.edit { preferences ->
            preferences[DYNAMIC_COLOR_KEY] = dynamicColor
        }
    }

    override fun currentSystem(): Flow<SystemID?> =
        store.data.map { preferences ->
            preferences[CURRENT_SYSTEM_KEY]?.let { SystemID(it) }
        }

    override suspend fun setCurrentSystem(id: SystemID) {
        store.edit { preferences ->
            preferences[CURRENT_SYSTEM_KEY] = id.value
        }
    }

    private companion object {
        val DYNAMIC_COLOR_KEY = booleanPreferencesKey("dynamicColor")
        val CURRENT_SYSTEM_KEY = stringPreferencesKey("currentSystem")
    }
}
