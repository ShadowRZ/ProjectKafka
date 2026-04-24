package io.github.shadowrz.projectkafka.libraries.data.impl

import okio.Path.Companion.toPath

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
