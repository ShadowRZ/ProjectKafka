package io.github.shadowrz.projectkafka.libraries.core.extensions

import com.eygraber.uri.Uri

fun Uri?.isNullOrEmpty() =
    if (this == null) {
        true
    } else {
        this == Uri.EMPTY
    }

fun Uri.toNullableUri() = if (this == Uri.EMPTY) null else this
