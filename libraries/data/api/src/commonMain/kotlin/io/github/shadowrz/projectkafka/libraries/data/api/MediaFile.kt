package io.github.shadowrz.projectkafka.libraries.data.api

@JvmInline
value class MediaFile(
    val value: String,
) {
    override fun toString(): String = value
}
