package io.github.shadowrz.projectkafka.libraries.preferences.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.github.shadowrz.projectkafka.libraries.preferences.api.AppPreferencesStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class LocalAppPreferencesStore : AppPreferencesStore {
    abstract val store: DataStore<Preferences>

    override fun dynamicColor(): Flow<Boolean> =
        store.data.map { preferences ->
            preferences[DYNAMIC_COLOR_KEY] == true
        }

    override suspend fun setDynamicColor(dynamicColor: Boolean) {
        store.edit { preferences ->
            preferences[DYNAMIC_COLOR_KEY] = dynamicColor
        }
    }

    override fun allowsMultiSystem(): Flow<Boolean> =
        store.data.map { preferences ->
            preferences[ALLOWS_MULTI_SYSTEM_KEY] == true
        }

    override suspend fun setAllowsMultiSystem(allowsMultiSystem: Boolean) {
        store.edit { preferences ->
            preferences[ALLOWS_MULTI_SYSTEM_KEY] = allowsMultiSystem
        }
    }

    override fun useSystemFont(): Flow<Boolean> =
        store.data.map { preferences ->
            preferences[USE_SYSTEM_FONT_KEY] == true
        }

    override suspend fun setUseSystemFont(useSystemFont: Boolean) {
        store.edit { preferences ->
            preferences[USE_SYSTEM_FONT_KEY] = useSystemFont
        }
    }

    private companion object {
        val DYNAMIC_COLOR_KEY = booleanPreferencesKey("dynamicColor")
        val ALLOWS_MULTI_SYSTEM_KEY = booleanPreferencesKey("allowsMultiSystem")

        val USE_SYSTEM_FONT_KEY = booleanPreferencesKey("useSystemFont")
    }
}
