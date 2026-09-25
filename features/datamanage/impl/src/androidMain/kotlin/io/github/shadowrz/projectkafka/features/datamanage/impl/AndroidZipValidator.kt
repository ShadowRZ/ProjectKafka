package io.github.shadowrz.projectkafka.features.datamanage.impl

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.source
import java.util.zip.ZipInputStream
import kotlin.io.path.createTempDirectory
import kotlinx.coroutines.withContext
import kotlinx.io.asInputStream
import kotlinx.io.buffered
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.source

@Inject
@ContributesBinding(AppScope::class)
class AndroidZipValidator(
    @CacheDirectory private val cacheDir: Path,
    private val fileSystem: FileSystem,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ZipValidator {
    private val logger = LoggerTag("DataRestore", LoggerTag.Root)

    override suspend fun unpackAndValidateZip(file: PlatformFile): ZipValidator.Result =
        withContext(coroutineDispatchers.io) {
            val tempDir = createTempDirectory(cacheDir.toNioPath(), "restore_")
            file.source().buffered().asInputStream().use { stream ->
                ZipInputStream(stream).use { it.unpackTo(tempDir.toOkioPath()) }
                ZipValidator.Result.Ok(tempDir.toOkioPath())
            }
        }

    private fun ZipInputStream.unpackTo(outdir: Path) {
        generateSequence { nextEntry }
            .forEach { entry ->
                if (entry.isDirectory) {
                    fileSystem.createDirectory(outdir / entry.name)
                } else {
                    fileSystem.write(
                        outdir.resolve(entry.name).also {
                            Logger.withTag(logger.value).d { "Unpacking to $it" }
                            it.parent?.let { parent ->
                                fileSystem.createDirectory(parent)
                            }
                        }
                    ) {
                        writeAll(source())
                    }
                }
            }
    }
}
