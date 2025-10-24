package io.github.shadowrz.projectkafka.libraries.zipwriter

import okio.BufferedSink
import okio.Path

internal class SubdirZipWriter(
    private val base: Path,
    private val inner: ZipWriter,
) : ZipWriter {
    override fun <T> file(
        file: Path,
        compress: Boolean,
        writerAction: BufferedSink.() -> T,
    ): T = inner.file(base / file, compress, writerAction)

    override fun directory(dir: Path) {
        inner.directory(base / dir)
    }

    override fun close() {
        // Closing this writer has no effect.
    }
}
