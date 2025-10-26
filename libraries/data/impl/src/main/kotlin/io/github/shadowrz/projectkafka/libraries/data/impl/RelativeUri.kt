package io.github.shadowrz.projectkafka.libraries.data.impl

import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri
import okio.Path
import okio.Path.Companion.toPath
import android.net.Uri as AndroidUri

fun Uri.toDbRelative(root: String) =
    if (this.scheme == "file") {
        this.path?.let { path ->
            val path = path.toPath().relativeTo(root.toPath()).toString()
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
            val path = root.toPath().resolve(path.removePrefix(Path.DIRECTORY_SEPARATOR))
            path.toString().toKmpUri()
        } ?: this
    } else {
        this
    }
