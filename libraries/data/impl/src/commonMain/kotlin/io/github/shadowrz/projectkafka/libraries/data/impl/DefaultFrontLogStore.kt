package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.core.IDGenerator
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.FrontLog
import io.github.shadowrz.projectkafka.libraries.data.api.FrontLogID
import io.github.shadowrz.projectkafka.libraries.data.api.FrontLogStore
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.impl.db.FrontLogField
import io.github.shadowrz.projectkafka.libraries.data.impl.db.FrontLogMember
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.Path
import kotlin.collections.map
import kotlin.time.Instant
import io.github.shadowrz.projectkafka.libraries.data.impl.db.FrontLog as DbFrontLog

@SingleIn(SystemScope::class)
@Inject
@ContributesBinding(SystemScope::class)
class DefaultFrontLogStore(
    private val systemDatabase: SystemDatabase,
    private val coroutineDispatchers: CoroutineDispatchers,
    @FilesDirectory private val filesDir: Path,
) : FrontLogStore {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFrontLogs(): Flow<List<FrontLog>> =
        systemDatabase.frontLogQueries
            .frontLogs {
                frontLogId,
                timestamp,
                description,
                memberId,
                memberName,
                memberDescription,
                memberAvatar,
                memberCover,
                memberPreferences,
                memberRoles,
                memberBirth,
                memberAdmin,
                ->

                Pair(
                    Triple(frontLogId, timestamp, description),
                    Member(
                        id = MemberID(memberId),
                        name = memberName,
                        description = memberDescription,
                        avatar = memberAvatar?.toAbsolute(filesDir.toString()),
                        cover = memberCover?.toAbsolute(filesDir.toString()),
                        preferences = memberPreferences,
                        roles = memberRoles,
                        birth = memberBirth,
                        admin = memberAdmin,
                    ),
                )
            }.asFlow()
            .mapToList(coroutineDispatchers.io)
            .map {
                buildMap {
                    it.forEach { (frontLog, member) ->
                        val (id, timestamp, description) = frontLog
                        getOrPut(id) { Triple(timestamp, description, mutableListOf()) }.third.add(member)
                    }
                }.map { item ->
                    val (timestamp, description, members) = item.value
                    FrontLog(
                        id = FrontLogID(item.key),
                        timestamp = timestamp,
                        members = members.toList(),
                        description = description,
                    )
                }
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFrontLog(id: FrontLogID): Flow<FrontLog> =
        systemDatabase.frontLogQueries
            .frontLogById(
                id.value,
            ) {
                timestamp,
                description,
                memberId,
                memberName,
                memberDescription,
                memberAvatar,
                memberCover,
                memberPreferences,
                memberRoles,
                memberBirth,
                memberAdmin,
                ->

                Triple(
                    timestamp,
                    description,
                    Member(
                        id = MemberID(memberId),
                        name = memberName,
                        description = memberDescription,
                        avatar = memberAvatar?.toAbsolute(filesDir.toString()),
                        cover = memberCover?.toAbsolute(filesDir.toString()),
                        preferences = memberPreferences,
                        roles = memberRoles,
                        birth = memberBirth,
                        admin = memberAdmin,
                    ),
                )
            }.asFlow()
            .mapToList(coroutineDispatchers.io)
            .map {
                val (timestamp, description) = it.first()
                val members = it.map { pair -> pair.third }

                FrontLog(
                    id = id,
                    timestamp = timestamp,
                    members = members,
                    description = description,
                )
            }

    override fun getFrontLogFields(id: FrontLogID): Flow<Map<String, String>> =
        systemDatabase.frontLogQueries
            .frontLogFields(id.value)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { fields ->
                buildMap {
                    fields.forEach { field ->
                        if (field.value_ != null) this[field.name] = field.value_
                    }
                }
            }.distinctUntilChanged()

    override suspend fun createFrontLog(
        description: String?,
        timestamp: Instant,
        members: List<Member>,
        fields: Map<String, String>,
    ) {
        withContext(coroutineDispatchers.io) {
            systemDatabase.transaction {
                val id = IDGenerator.generate()
                systemDatabase.frontLogQueries.insertFrontLog(
                    DbFrontLog(
                        id = id,
                        timestamp = timestamp,
                        description = description,
                    ),
                )
                members
                    .map {
                        FrontLogMember(
                            frontLogId = id,
                            memberId = it.id.value,
                        )
                    }.forEach {
                        systemDatabase.frontLogQueries.insertFrontLogMembers(it)
                    }
                fields
                    .map {
                        FrontLogField(
                            frontLogId = id,
                            name = it.key,
                            value_ = it.value,
                        )
                    }.forEach {
                        systemDatabase.frontLogQueries.insertFrontLogFields(it)
                    }
            }
        }
    }

    override suspend fun updateFrontLogFields(
        id: FrontLogID,
        fields: Map<String, String?>,
    ) {
        withContext(coroutineDispatchers.io) {
            systemDatabase.transaction {
                fields.forEach { (key, value) ->
                    if (value != null) {
                        systemDatabase.frontLogQueries.removeFrontLogField(id.value, key)
                    } else {
                        systemDatabase.frontLogQueries.updateFrontLogField(value, key, id.value)
                    }
                }
            }
        }
    }

    override suspend fun removeFrontLog(id: FrontLogID) {
        withContext(coroutineDispatchers.io) {
            systemDatabase.frontLogQueries.removeFrontLog(id.value)
        }
    }

    override suspend fun updateFrontLogMembers(
        id: FrontLogID,
        members: List<Member>,
    ) {
        withContext(coroutineDispatchers.io) {
            systemDatabase.transaction {
                systemDatabase.frontLogQueries.removeFrontLogMembers(id.value)
                members.forEach {
                    systemDatabase.frontLogQueries.insertFrontLogMembers(
                        FrontLogMember(
                            frontLogId = id.value,
                            memberId = it.id.value,
                        ),
                    )
                }
            }
        }
    }
}
