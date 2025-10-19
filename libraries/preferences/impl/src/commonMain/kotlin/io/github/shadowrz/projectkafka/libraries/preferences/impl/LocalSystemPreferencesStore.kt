package io.github.shadowrz.projectkafka.libraries.preferences.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.preferences.api.SystemPreferencesStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class LocalSystemPreferencesStore : SystemPreferencesStore {
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

    override fun fronters(): Flow<Set<MemberID>> =
        store.data.map { preferences ->
            preferences[FRONTERS_KEY]
                ?.map {
                    MemberID(it)
                }?.toSet() ?: emptySet()
        }

    override suspend fun setFronters(fronters: Set<MemberID>) {
        store.edit { preferences ->
            preferences[FRONTERS_KEY] = fronters.map { it.toString() }.toSet()
        }
    }

    private companion object {
        val DYNAMIC_COLOR_KEY = booleanPreferencesKey("dynamic_color")
        val FRONTERS_KEY = stringSetPreferencesKey("fronters")
    }
}
