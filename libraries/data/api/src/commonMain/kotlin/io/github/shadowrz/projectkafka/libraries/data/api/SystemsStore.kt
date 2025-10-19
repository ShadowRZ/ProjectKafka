package io.github.shadowrz.projectkafka.libraries.data.api

import com.eygraber.uri.Uri
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

interface SystemsStore {
    // Reading
    fun getSystems(): Flow<List<System>>

    suspend fun getSystem(id: SystemID): System

    // Writing
    suspend fun createSystem(
        name: String,
        description: String?,
        avatar: Uri?,
        cover: Uri?,
    ): SystemID

    fun lastSystemID(): Flow<SystemID?>

    @OptIn(ExperimentalTime::class)
    suspend fun updateSystemLastUsed(
        id: SystemID,
        lastUsed: Instant,
    )
}
