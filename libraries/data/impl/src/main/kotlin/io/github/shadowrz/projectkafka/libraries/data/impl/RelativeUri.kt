package io.github.shadowrz.projectkafka.libraries.data.impl

import androidx.core.net.toUri
import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri
import java.io.File
import android.net.Uri as AndroidUri

fun Uri.toDbRelative(root: String) =
    if (this.scheme == "file") {
        this.path?.let { path ->
            val path = File(path).relativeTo(File(root)).path
            AndroidUri
                .Builder()
                .scheme("io.github.shadowrz.projectkafka.internal")
                .path(path)
                .build()
                .toKmpUri()
        } ?: this
    } else {
        this
    }

fun Uri.toAbsolute(root: String) =
    if (this.scheme == "io.github.shadowrz.projectkafka.internal") {
        this.path?.let { path ->
            val path = File(root).resolve(path.removePrefix(File.separator))
            path.toUri().toKmpUri()
        } ?: this
    } else {
        this
    }
