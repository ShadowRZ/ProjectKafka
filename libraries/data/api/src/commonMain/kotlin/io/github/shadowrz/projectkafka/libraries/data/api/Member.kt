package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDate

@Stable
data class Member(
    val id: MemberID,
    val name: String,
    val description: String?,
    val avatar: MediaFile?,
    val cover: MediaFile?,
    val preferences: String?,
    val roles: String?,
    val birth: LocalDate?,
    val admin: Boolean,
    val fields: Map<String, String?> = emptyMap(),
)
