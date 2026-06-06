package io.github.shadowrz.projectkafka.libraries.data.api

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow

interface SystemsStore {
    // Reading
    fun getSystems(): Flow<List<System>>

    suspend fun getSystem(id: SystemID): System

    // Writing
    suspend fun createSystem(
        name: String,
        description: String?,
        avatar: MediaFile?,
        cover: MediaFile?,
    ): SystemID

    fun lastSystemID(): Flow<SystemID?>

    @OptIn(ExperimentalTime::class)
    suspend fun updateSystemLastUsed(
        id: SystemID,
        lastUsed: Instant,
    )
}
