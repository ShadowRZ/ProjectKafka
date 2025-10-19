package io.github.shadowrz.projectkafka.libraries.data.api

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MessageID(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
