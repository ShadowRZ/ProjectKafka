package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.content.Context
import co.touchlab.kermit.Logger
import com.eygraber.uri.Uri
import com.eygraber.uri.toAndroidUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.source
import java.util.zip.ZipInputStream
import kotlin.io.path.createTempDirectory

@Inject
@ContributesBinding(AppScope::class)
class AndroidZipValidator(
    @ApplicationContext private val context: Context,
    @CacheDirectory private val cacheDir: Path,
    private val fileSystem: FileSystem,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ZipValidator {
    private val logger = LoggerTag("DataRestore", LoggerTag.Root)

    override suspend fun unpackAndValidateZip(input: Uri): ZipValidator.Result =
        withContext(coroutineDispatchers.io) {
            val tempDir = createTempDirectory(cacheDir.toNioPath(), "restore_")
            context.contentResolver.openInputStream(input.toAndroidUri())?.use { stream ->
                ZipInputStream(stream).use { it.unpackTo(tempDir.toOkioPath()) }
                ZipValidator.Result.Ok(tempDir.toOkioPath())
            } ?: ZipValidator.Result.Invalid
        }

    private fun ZipInputStream.unpackTo(outdir: Path) {
        generateSequence { nextEntry }.forEach { entry ->
            if (entry.isDirectory) {
                fileSystem.createDirectory(outdir / entry.name)
            } else {
                fileSystem.write(
                    outdir.resolve(entry.name).also {
                        Logger.withTag(logger.value).d { "Unpacking to $it" }
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
