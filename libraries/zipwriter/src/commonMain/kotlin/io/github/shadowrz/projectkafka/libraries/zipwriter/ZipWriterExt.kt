package io.github.shadowrz.projectkafka.libraries.zipwriter

import okio.Path
import okio.Path.Companion.toPath
import okio.Source

fun <T> ZipWriter.directory(
    dir: Path,
    content: ZipWriter.() -> T,
): T =
    run {
        directory(dir)
        SubdirZipWriter(dir, this).content()
    }

fun <T> ZipWriter.directory(
    dir: String,
    content: ZipWriter.() -> T,
): T = directory(dir.toPath(), content)

fun ZipWriter.file(
    file: Path,
    compress: Boolean,
    source: () -> Source,
): Unit =
    file(file, compress) {
        writeAll(source())
    }

fun ZipWriter.file(
    file: String,
    compress: Boolean,
    source: () -> Source,
): Unit = file(file.toPath(), compress, source)
