package io.github.shadowrz.projectkafka.libraries.zipwriter

import okio.Path
import okio.Path.Companion.toPath
import okio.source
import java.io.File
import java.io.FileInputStream

fun ZipWriter.file(
    file: Path,
    from: File,
    compress: Boolean = true,
): Unit =
    file(file, compress) {
        FileInputStream(from).use {
            writeAll(it.source())
        }
    }

fun ZipWriter.file(
    file: String,
    from: File,
    compress: Boolean = true,
): Unit = file(file.toPath(), from, compress)
