package io.github.shadowrz.projectkafka.libraries.data.test

import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
class InMemoryMembersStore(
    initialMembers: List<Member> = emptyList(),
) : MembersStore {
    private val members = MutableStateFlow(initialMembers)

    override fun getMembers(): Flow<List<Member>> = members.asStateFlow()

    override fun membersCount(): Flow<Long> = members.asStateFlow().map { it.size.toLong() }

    override fun getMember(id: MemberID): Flow<Member> = members.asStateFlow().map { members -> members.first { it.id == id } }

    override fun getMemberByIDs(ids: List<MemberID>): Flow<List<Member>> =
        members.asStateFlow().map { members ->
            members.filter {
                ids.contains(it.id)
            }
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
    ): Member {
        val model =
            Member(
                id = MemberID(Uuid.random().toHexString()),
                name = name,
                description = description,
                avatar = avatar,
                cover = cover,
                preferences = preferences,
                roles = roles,
                birth = birth,
                admin = admin,
                fields = fields ?: emptyMap(),
            )

        members.update {
            listOf(*it.toTypedArray(), model)
        }

        return model
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
    ) {
        val member = members.value.first { it.id == id }
        val newMember =
            member.copy(
                name = name,
                description = description,
                avatar = avatar,
                cover = cover,
                preferences = preferences,
                roles = roles,
                birth = birth,
                admin = admin,
                fields = fields ?: emptyMap(),
            )

        members.update { members ->
            listOf(*members.filter { it.id != id }.toTypedArray(), newMember)
        }
    }
}
