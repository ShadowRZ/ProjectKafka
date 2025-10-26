package io.github.shadowrz.projectkafka.libraries.zipwriter

import okio.FileSystem
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

fun ZipWriter.directory(
    dir: String,
    root: File,
) {
    if (!root.isDirectory) return

    directory(dir) {
        root
            .walk()
            .filter { it.isFile }
            .forEach { file(it.toRelativeString(root), it) }
    }
}

fun ZipWriter.directory(
    dir: String,
    root: Path,
    fileSystem: FileSystem = FileSystem.SYSTEM,
) {
    val metadata = fileSystem.metadata(root)
    if (!metadata.isDirectory) return

    directory(dir) {
        fileSystem.listRecursively(root, followSymlinks = false).filter { fileSystem.metadata(it).isRegularFile }.forEach {
            file(it.relativeTo(root).toString()) {
                fileSystem.read(it) {
                    writeAll(this)
                }
            }
        }
    }
}
