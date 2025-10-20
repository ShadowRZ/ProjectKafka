package io.github.shadowrz.projectkafka.libraries.data.api

import com.eygraber.uri.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface MembersStore {
    // Reading
    fun getMembers(): Flow<List<Member>>

    fun membersCount(): Flow<Long>

    fun getMember(id: MemberID): Flow<Member?>

    fun getMemberByIDs(ids: List<MemberID>): Flow<List<Member>>

    // Writing
    suspend fun createMember(
        name: String,
        description: String?,
        avatar: Uri?,
        cover: Uri?,
        preferences: String?,
        roles: String?,
        birth: LocalDate?,
        admin: Boolean,
        fields: Map<String, String?>? = null,
    ): Member

    suspend fun updateMember(
        id: MemberID,
        name: String,
        description: String?,
        avatar: Uri?,
        cover: Uri?,
        preferences: String?,
        roles: String?,
        birth: LocalDate?,
        admin: Boolean,
        fields: Map<String, String?>? = null,
    )

    suspend fun deleteMember(id: MemberID)
}
