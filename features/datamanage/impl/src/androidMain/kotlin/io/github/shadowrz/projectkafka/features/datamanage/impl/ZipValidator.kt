package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.content.Context
import android.net.Uri
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.source
import timber.log.Timber
import java.util.zip.ZipInputStream

@Inject
class ZipValidator(
    @ApplicationContext private val context: Context,
    private val fileSystem: FileSystem,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    private val logger = LoggerTag("DataRestore", LoggerTag.Root)

    suspend fun unpackAndValidateZip(output: Uri): Result =
        withContext(coroutineDispatchers.io) {
            context.contentResolver.openInputStream(output)?.use { stream ->
                val tempDir = context.createTempDirectory("restore_")
                ZipInputStream(stream).use { it.unpackTo(tempDir.toOkioPath()) }
                Result.Ok(tempDir.toOkioPath())
            } ?: Result.Invalid
        }

    private fun ZipInputStream.unpackTo(outdir: Path) {
        generateSequence { nextEntry }.forEach { entry ->
            if (entry.isDirectory) {
                fileSystem.createDirectory(outdir / entry.name)
            } else {
                fileSystem.write(
                    outdir.resolve(entry.name).also {
                        Timber.tag(logger.value).d("Unpacking to %s", it.toString())
                        it.parent?.let { parent ->
                            fileSystem.createDirectory(parent)
                        }
                    },
                ) {
                    writeAll(source())
                }
            }
        }
    }

    sealed interface Result {
        data class Ok(
            val unpacked: Path,
        ) : Result

        data object Invalid : Result
    }
}
