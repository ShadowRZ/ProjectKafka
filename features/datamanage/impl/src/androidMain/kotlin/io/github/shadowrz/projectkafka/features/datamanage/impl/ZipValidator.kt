package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.content.Context
import android.net.Uri
import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.navigation.di.SystemGraphCache
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.util.zip.ZipInputStream

@Inject
class ZipValidator(
    @ApplicationContext private val context: Context,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun unpackAndValidateZip(output: Uri): Result =
        withContext(coroutineDispatchers.io) {
            context.contentResolver.openInputStream(output)?.use { stream ->
                val zipInputStream = ZipInputStream(stream)
                val tempDir = Files.createTempDirectory(context.cacheDir.toPath(), "restore_").toFile()
                unpackZip(zipInputStream, tempDir)

                Result.Ok(tempDir)
            } ?: Result.Invalid
        }

    private fun unpackZip(
        zipInputStream: ZipInputStream,
        outdir: File,
    ) {
        var zipEntry = zipInputStream.nextEntry

        while (zipEntry != null) {
            val name = zipEntry.name
            val outfile = outdir.resolve(name)
            outfile.parentFile?.mkdirs()
            FileOutputStream(outfile).use { stream ->
                with(zipInputStream) {
                    copyTo(stream)
                }
            }
            zipEntry = zipInputStream.nextEntry
        }
    }

    sealed interface Result {
        data class Ok(
            val unpacked: File,
        ) : Result

        data object Invalid : Result
    }
}
