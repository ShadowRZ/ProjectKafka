package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
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
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.FrontLogField
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.FrontLogMember
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.SystemDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.time.Instant
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.FrontLog as DbFrontLog

@SingleIn(SystemScope::class)
@Inject
@ContributesBinding(SystemScope::class)
class DefaultFrontLogStore(
    private val membersStore: MembersStore,
    private val systemDatabase: SystemDatabase,
    private val coroutineDispatchers: CoroutineDispatchers,
) : FrontLogStore {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFrontLogs(): Flow<List<FrontLog.WithoutMembers>> =
        systemDatabase.frontLogQueries
            .frontLogs { id, timestamp ->
                FrontLog.WithoutMembers(
                    id = FrontLogID(id),
                    timestamp = timestamp,
                )
            }.asFlow()
            .mapToList(coroutineDispatchers.io)
            .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFrontLog(id: FrontLogID): Flow<FrontLog> =
        combineTransform(
            systemDatabase.frontLogQueries
                .frontLogById(id.value)
                .asFlow()
                .mapToOne(coroutineDispatchers.io)
                .distinctUntilChanged(),
            getFrontLogFields(id),
            getFrontLogMembers(id),
        ) { frontLog, fields, members ->
            FrontLog(
                id = FrontLogID(frontLog.id),
                timestamp = frontLog.timestamp,
                members = members,
                fields = fields,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFrontLogMembers(id: FrontLogID): Flow<List<Member>> =
        systemDatabase.frontLogQueries
            .frontLogMembers(id.value)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .flatMapMerge {
                membersStore.getMemberByIDs(it.map { id -> MemberID(id) })
            }.distinctUntilChanged()

    override suspend fun createFrontLog(
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
