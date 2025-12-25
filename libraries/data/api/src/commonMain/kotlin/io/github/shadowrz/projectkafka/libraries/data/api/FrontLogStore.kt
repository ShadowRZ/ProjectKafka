package io.github.shadowrz.projectkafka.libraries.data.api

import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

interface FrontLogStore {
    // Reading
    fun getFrontLogs(): Flow<List<FrontLog>>

    fun getFrontLog(id: FrontLogID): Flow<FrontLog>

    fun getFrontLogFields(id: FrontLogID): Flow<Map<String, String>>

    // Writing
    suspend fun createFrontLog(
        timestamp: Instant,
        members: List<Member>,
        fields: Map<String, String> = emptyMap(),
    )

    suspend fun updateFrontLogFields(
        id: FrontLogID,
        fields: Map<String, String?> = emptyMap(),
    )

    suspend fun removeFrontLog(id: FrontLogID)

    suspend fun updateFrontLogMembers(
        id: FrontLogID,
        members: List<Member>,
    )
}
