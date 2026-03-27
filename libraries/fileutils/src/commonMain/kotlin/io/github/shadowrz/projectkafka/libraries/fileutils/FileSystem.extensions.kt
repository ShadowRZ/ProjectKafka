package io.github.shadowrz.projectkafka.libraries.fileutils

import okio.BufferedSink
import okio.FileSystem
import okio.Path
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun FileSystem.createTempFile(
    baseDir: Path,
    extension: String? = null,
): Path {
    createDirectories(baseDir)
    val ext = extension?.let { ".$it" }
    val basename = Uuid.generateV4().toString()
    val filename = "$basename$ext"

    val path = baseDir / filename

    write(path) {}

    return path
}

@OptIn(ExperimentalUuidApi::class)
fun FileSystem.writeTempFile(
    baseDir: Path,
    extension: String? = null,
    writerAction: BufferedSink.() -> Unit,
): Path {
    createDirectories(baseDir)
    val ext = extension?.let { ".$it" }
    val basename = Uuid.generateV4().toString()
    val filename = "$basename$ext"

    val path = baseDir / filename

    write(path) {
        writerAction()
    }

    return path
}
