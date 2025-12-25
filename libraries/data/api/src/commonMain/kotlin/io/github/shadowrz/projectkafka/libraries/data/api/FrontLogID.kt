package io.github.shadowrz.projectkafka.libraries.data.api

import kotlinx.serialization.Serializable

/**
 * Denotes a FrontLog ID.
 */
@JvmInline
@Serializable
value class FrontLogID(
    val value: String,
) {
    override fun toString(): String = value
}
