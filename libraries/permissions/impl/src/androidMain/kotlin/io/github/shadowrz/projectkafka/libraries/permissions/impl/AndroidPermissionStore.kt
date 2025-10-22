package io.github.shadowrz.projectkafka.libraries.permissions.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.di.annotations.IOScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@SingleIn(AppScope::class)
@Inject
@ContributesBinding(AppScope::class)
class AndroidPermissionStore(
    @ApplicationContext context: Context,
    @IOScope private val scope: CoroutineScope,
) : PermissionStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("projectkafka.permissions", scope = scope)

    private val store = context.dataStore

    override fun hasRequested(permission: String): Flow<Boolean> =
        store.data.map {
            it[requestedKey(permission)] ?: false
        }

    override suspend fun setHasRequested(
        permission: String,
        hasRequested: Boolean,
    ) {
        store.edit {
            it[requestedKey(permission)] = hasRequested
        }
    }

    override fun hasRejected(permission: String): Flow<Boolean> =
        store.data.map {
            it[rejectedKey(permission)] ?: false
        }

    override suspend fun setHasRejected(
        permission: String,
        hasRejected: Boolean,
    ) {
        store.edit {
            it[rejectedKey(permission)] = hasRejected
        }
    }

    private fun requestedKey(permission: String) = booleanPreferencesKey("${permission}_requested")

    private fun rejectedKey(permission: String) = booleanPreferencesKey("${permission}_rejected")
}
