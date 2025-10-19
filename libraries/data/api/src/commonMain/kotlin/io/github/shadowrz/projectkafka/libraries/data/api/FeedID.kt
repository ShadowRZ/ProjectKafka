package io.github.shadowrz.projectkafka.libraries.data.api

import kotlinx.serialization.Serializable

/**
 * Denotes a Feed ID.
 */
@JvmInline
@Serializable
value class FeedID(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
