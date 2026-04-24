package io.github.shadowrz.projectkafka.libraries.data.impl

import io.github.shadowrz.projectkafka.libraries.data.api.MediaFile
import okio.Buffer
import okio.FileSystem
import okio.HashingSink
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer

/**
 * Rewrite MediaFile to a persisted path.
 */
context(fileSystem: FileSystem)
internal fun MediaFile.rewriteToPersisted(
    filesDir: Path,
    cacheDir: Path,
): String {
    val path = this.value
    // If file is in cacheDir
    return if (path.startsWith(cacheDir.toString())) {
        val path = fileSystem.writeAsHashed(filesDir = filesDir, path = path.toPath(normalize = true))
        path.toString().toRelative(root = filesDir.toString())
    } else {
        this.value.toRelative(root = filesDir.toString())
    }
}

private fun FileSystem.writeAsHashed(
    filesDir: Path,
    path: Path,
): Path {
    val buffer = Buffer()
    val sink = HashingSink.sha256(buffer)
    val source = this.source(path)
    return sink.use { sink ->
        sink.buffer().use { buffer ->
            buffer.writeAll(source)
            buffer.flush()
        }
        sink.flush()
        val output = filesDir / "assets" / "${sink.hash.hex()}.webp"
        this.write(
            output.also { output ->
                output.parent?.let { parent ->
                    this.createDirectory(parent)
                }
            },
        ) {
            writeAll(buffer)
            flush()
        }

        this.delete(path = path, mustExist = false)
        output
    }
}
