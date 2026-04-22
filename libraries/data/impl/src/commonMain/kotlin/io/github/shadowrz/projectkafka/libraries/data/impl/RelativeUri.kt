package io.github.shadowrz.projectkafka.libraries.data.impl

import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri
import okio.Buffer
import okio.FileSystem
import okio.HashingSink
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer

fun Uri.toDbRelative(root: String) =
    when (scheme) {
        null, "file" -> {
            this.path?.toRelative(root)?.toKmpUri() ?: Uri.EMPTY
        }

        else -> {
            this
        }
    }

fun Uri.toAbsolute(root: String): Uri =
    when (scheme) {
        null -> {
            this.path?.toAbsolute(root)?.toKmpUri() ?: Uri.EMPTY
        }

        "io.github.shadowrz.projectkafka.internal" -> {
            this
                .toString()
                .removePrefix(
                    "io.github.shadowrz.projectkafka.internal:",
                ).toAbsolute(root)
                .toKmpUri()
        }

        else -> {
            this
        }
    }

internal fun String.toAbsolute(root: String): String {
    val isAbsolute = this.startsWith("/")
    return if (isAbsolute) {
        this.toPath(normalize = true).toString()
    } else {
        "$root/$this".toPath(normalize = true).toString()
    }
}

internal fun String.toRelative(root: String): String {
    val rootPath = root.toPath(normalize = true)
    val path = this.toPath(normalize = true)

    val resolved = rootPath.resolve(path, normalize = true)
    return if (resolved.toString().startsWith(root)) {
        resolved.relativeTo(rootPath).toString()
    } else {
        resolved.toString()
    }
}
