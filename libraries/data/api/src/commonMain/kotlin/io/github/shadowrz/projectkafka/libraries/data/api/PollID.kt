package io.github.shadowrz.projectkafka.libraries.data.api

import kotlinx.serialization.Serializable

/**
 * Denotes a Poll ID.
 */
@JvmInline
@Serializable
value class PollID(
    val value: String,
) {
    override fun toString(): String = value
}
