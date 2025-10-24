package io.github.shadowrz.projectkafka.libraries.zipwriter

import okio.BufferedSink
import okio.Path
import okio.buffer
import okio.sink
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

actual inline fun <T> BufferedSink.writeZip(writerAction: ZipWriter.() -> T): T = outputStream().writeZip(writerAction)

inline fun <T> OutputStream.writeZip(writerAction: ZipWriter.() -> T): T =
    JavaZipWriter(ZipOutputStream(this)).use {
        it.writerAction()
    }

class JavaZipWriter(
    private val stream: ZipOutputStream,
) : ZipWriter {
    override fun <T> file(
        file: Path,
        compress: Boolean,
        writerAction: BufferedSink.() -> T,
    ): T =
        stream.run {
            putNextEntry(
                ZipEntry(file.toString()).apply {
                    method = if (compress) ZipEntry.DEFLATED else ZipEntry.STORED
                },
            )
            stream
                .sink()
                .buffer()
                .run {
                    writerAction().also { flush() }
                }.apply { closeEntry() }
        }

    override fun directory(dir: Path) {
        stream.run {
            putNextEntry(ZipEntry("${dir.toString().removeSuffix("/")}/"))
            closeEntry()
        }
    }

    override fun close() {
        stream.finish()
    }
}
