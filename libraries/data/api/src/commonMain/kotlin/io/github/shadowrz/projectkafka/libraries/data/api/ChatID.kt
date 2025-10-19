package io.github.shadowrz.projectkafka.libraries.data.api

import kotlinx.serialization.Serializable

/**
 * Denotes a Chat ID.
 */
@JvmInline
@Serializable
value class ChatID(
    val value: String,
) {
    override fun toString(): String = value
}
