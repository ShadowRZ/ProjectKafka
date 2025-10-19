package io.github.shadowrz.projectkafka.libraries.permissions.impl

import kotlinx.coroutines.flow.Flow

interface PermissionStore {
    fun hasRequested(permission: String): Flow<Boolean>

    suspend fun setHasRequested(
        permission: String,
        hasRequested: Boolean,
    )

    fun hasRejected(permission: String): Flow<Boolean>

    suspend fun setHasRejected(
        permission: String,
        hasRejected: Boolean,
    )
}
