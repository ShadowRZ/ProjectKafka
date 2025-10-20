package io.github.shadowrz.projectkafka.libraries.core.extensions

fun String.toNullableString() = ifEmpty { null }
