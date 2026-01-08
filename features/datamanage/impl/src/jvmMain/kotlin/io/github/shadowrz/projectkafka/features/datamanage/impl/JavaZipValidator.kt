package io.github.shadowrz.projectkafka.features.datamanage.impl

import com.eygraber.uri.Uri
import com.eygraber.uri.toURI
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.source
import java.util.zip.ZipInputStream
import kotlin.io.path.createTempDirectory
import kotlin.io.path.inputStream
import kotlin.io.path.toPath

@Inject
@ContributesBinding(AppScope::class)
class JavaZipValidator(
    @CacheDirectory private val cacheDir: Path,
    private val fileSystem: FileSystem,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ZipValidator {
    override suspend fun unpackAndValidateZip(input: Uri): ZipValidator.Result =
        withContext(coroutineDispatchers.io) {
            val tempDir = createTempDirectory(cacheDir.toNioPath(), "restore_")
            ZipInputStream(
                input.toURI().toPath().inputStream(),
            ).use { it.unpackTo(tempDir.toOkioPath()) }
            ZipValidator.Result.Ok(tempDir.toOkioPath())
        }

    private fun ZipInputStream.unpackTo(outdir: Path) {
        generateSequence { nextEntry }.forEach { entry ->
            if (entry.isDirectory) {
                fileSystem.createDirectory(outdir / entry.name)
            } else {
                fileSystem.write(
                    outdir.resolve(entry.name).also {
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
}
