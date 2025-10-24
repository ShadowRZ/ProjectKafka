package io.github.shadowrz.projectkafka.libraries.zipwriter

import okio.BufferedSink
import okio.Closeable
import okio.Path
import okio.Path.Companion.toPath
import okio.Sink
import okio.buffer

interface ZipWriter : Closeable {
    fun <T> file(
        file: Path,
        compress: Boolean = true,
        writerAction: BufferedSink.() -> T,
    ): T

    fun directory(dir: Path)
}

expect inline fun <T> BufferedSink.writeZip(writerAction: ZipWriter.() -> T): T

inline fun <T> Sink.writeZip(writerAction: ZipWriter.() -> T): T = buffer().writeZip(writerAction)

fun <T> ZipWriter.file(
    file: String,
    compress: Boolean = true,
    writerAction: BufferedSink.() -> T,
) = file(file.toPath(), compress, writerAction)

fun ZipWriter.directory(dir: String) = directory(dir.toPath())
