package io.github.shadowrz.projectkafka.libraries.data.api

import kotlinx.serialization.Serializable

/**
 * Denotes a System ID.
 */
@JvmInline
@Serializable
value class SystemID(
    val value: String,
) {
    override fun toString(): String = value
}
