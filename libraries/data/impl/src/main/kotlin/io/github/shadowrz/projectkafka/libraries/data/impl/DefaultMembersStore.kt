package io.github.shadowrz.projectkafka.libraries.data.impl

import android.content.Context
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.eygraber.uri.Uri
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.core.IDGenerator
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.data.impl.db.toDbModel
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.MemberField
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.SystemDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.datetime.LocalDate

@SingleIn(SystemScope::class)
@Inject
@ContributesBinding(SystemScope::class)
class DefaultMembersStore(
    private val systemDatabase: SystemDatabase,
    private val coroutineDispatchers: CoroutineDispatchers,
    @ApplicationContext private val context: Context,
) : MembersStore {
    override fun getMembers(): Flow<List<Member>> =
        systemDatabase.memberQueries
            .members { id, name, description, avatar, cover, preferences, roles, birth, admin ->
                Member(
                    id = MemberID(id),
                    name = name,
                    description = description,
                    avatar = avatar?.toAbsolute(context.filesDir.absolutePath),
                    cover = cover?.toAbsolute(context.filesDir.absolutePath),
                    preferences = preferences,
                    roles = roles,
                    birth = birth,
                    admin = admin,
                )
            }.asFlow()
            .mapToList(coroutineDispatchers.io)

    override fun membersCount(): Flow<Long> =
        systemDatabase.memberQueries
            .membersCount()
            .asFlow()
            .mapToOne(coroutineDispatchers.io)
            .distinctUntilChanged()

    override fun getMember(id: MemberID): Flow<Member?> =
        combine(
            systemDatabase.memberQueries
                .memberById(
                    id.value,
                ) { id, name, description, avatar, cover, preferences, roles, birth, admin ->
                    Member(
                        id = MemberID(id),
                        name = name,
                        description = description,
                        avatar = avatar?.toAbsolute(context.filesDir.absolutePath),
                        cover = cover?.toAbsolute(context.filesDir.absolutePath),
                        preferences = preferences,
                        roles = roles,
                        birth = birth,
                        admin = admin,
                    )
                }.asFlow()
                .mapToOneOrNull(coroutineDispatchers.io),
            systemDatabase.memberQueries
                .memberFields(id.value)
                .asFlow()
                .mapToList(coroutineDispatchers.io),
        ) { member, fields ->
            val fields = fields.associate { it.name to it.value_ }
            member?.copy(fields = fields)
        }

    override fun getMemberByIDs(ids: List<MemberID>): Flow<List<Member>> {
        val ids = ids.map { it.value }
        return systemDatabase.memberQueries
            .memberByIds(
                ids,
            ) { id, name, description, avatar, cover, preferences, roles, birth, admin ->
                Member(
                    id = MemberID(id),
                    name = name,
                    description = description,
                    avatar = avatar?.toAbsolute(context.filesDir.absolutePath),
                    cover = cover?.toAbsolute(context.filesDir.absolutePath),
                    preferences = preferences,
                    roles = roles,
                    birth = birth,
                    admin = admin,
                )
            }.asFlow()
            .mapToList(coroutineDispatchers.io)
    }

    override suspend fun createMember(
        name: String,
        description: String?,
        avatar: Uri?,
        cover: Uri?,
        preferences: String?,
        roles: String?,
        birth: LocalDate?,
        admin: Boolean,
        fields: Map<String, String?>?,
    ): Member =
        systemDatabase.transactionWithResult {
            val model =
                Member(
                    id = MemberID(IDGenerator.generate()),
                    name = name,
                    description = description,
                    avatar = avatar?.toDbRelative(context.filesDir.absolutePath),
                    cover = cover?.toDbRelative(context.filesDir.absolutePath),
                    preferences = preferences,
                    roles = roles,
                    birth = birth,
                    admin = admin,
                )
            systemDatabase.memberQueries.insertMember(model.toDbModel())

            fields?.forEach {
                systemDatabase.memberQueries.insertMemberFields(
                    MemberField(
                        memberId = model.id.value,
                        name = it.key,
                        value_ = it.value,
                    ),
                )
            }

            model
        }

    override suspend fun updateMember(
        id: MemberID,
        name: String,
        description: String?,
        avatar: Uri?,
        cover: Uri?,
        preferences: String?,
        roles: String?,
        birth: LocalDate?,
        admin: Boolean,
        fields: Map<String, String?>?,
    ) = systemDatabase.transaction {
        systemDatabase.memberQueries.updateMember(
            Member(
                id = id,
                name = name,
                description = description,
                avatar = avatar?.toDbRelative(context.filesDir.absolutePath),
                cover = cover?.toDbRelative(context.filesDir.absolutePath),
                preferences = preferences,
                roles = roles,
                birth = birth,
                admin = admin,
            ).toDbModel(),
        )

        systemDatabase.memberQueries.removeMemberFields(id.value)

        fields?.forEach {
            systemDatabase.memberQueries.insertMemberFields(
                MemberField(
                    memberId = id.value,
                    name = it.key,
                    value_ = it.value,
                ),
            )
        }
    }

    override suspend fun deleteMember(id: MemberID) {
        systemDatabase.memberQueries.removeMember(id.value)
    }
}
