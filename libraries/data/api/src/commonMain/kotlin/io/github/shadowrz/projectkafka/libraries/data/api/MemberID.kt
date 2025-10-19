package io.github.shadowrz.projectkafka.libraries.data.api

import kotlinx.serialization.Serializable

/**
 * Denotes a System Member ID.
 */
@JvmInline
@Serializable
value class MemberID(
    val value: String,
) {
    override fun toString(): String = value
}
