package io.github.shadowrz.projectkafka.libraries.preferences.api

import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import kotlinx.coroutines.flow.Flow

interface SystemPreferencesStore {
    fun dynamicColor(): Flow<Boolean>

    suspend fun setDynamicColor(dynamicColor: Boolean)

    fun fronters(): Flow<Set<MemberID>>

    suspend fun setFronters(fronters: Set<MemberID>)
}
