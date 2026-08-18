package io.github.shadowrz.projectkafka.libraries.fileutils

import io.github.shadowrz.projectkafka.libraries.uniqueid.UniqueID
import kotlin.uuid.ExperimentalUuidApi
import okio.BufferedSink
import okio.FileSystem
import okio.Path

@OptIn(ExperimentalUuidApi::class)
fun FileSystem.createTempFile(
    baseDir: Path,
    extension: String? = null,
    uniqueID: UniqueID = UniqueID.Uuid,
): Path {
    createDirectories(baseDir)
    val ext = extension?.let { ".$it" }.orEmpty()
    val basename = uniqueID.generate()
    val filename = "$basename$ext"

    val path = baseDir / filename

    write(path) {}

    return path
}

@OptIn(ExperimentalUuidApi::class)
fun FileSystem.writeTempFile(
    baseDir: Path,
    extension: String? = null,
    uniqueID: UniqueID = UniqueID.Uuid,
    writerAction: BufferedSink.() -> Unit,
): Path {
    createDirectories(baseDir)
    val ext = extension?.let { ".$it" }.orEmpty()
    val basename = uniqueID.generate()
    val filename = "$basename$ext"

    val path = baseDir / filename

    write(path) {
        writerAction()
    }

    return path
}
