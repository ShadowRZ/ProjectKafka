package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.content.Context
import android.net.Uri
import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.SystemBindings
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.navigation.di.SystemGraphCache
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Inject
class ZipPackager(
    @ApplicationContext private val context: Context,
    @ForScope(AppScope::class) private val sqlDriver: SqlDriver,
    private val systemGraphCache: SystemGraphCache,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun packageZip(output: Uri) =
        withContext(coroutineDispatchers.io) {
            context.contentResolver.openOutputStream(output)?.use { stream ->
                ZipOutputStream(stream).use { zipOutputStream ->
                    val file = File.createTempFile("export_", ".db", context.cacheDir)
                    zipOutputStream.putNextEntry(ZipEntry("databases/projectkafka.db"))
                    sqlDriver.execute(
                        null,
                        "VACUUM main INTO ?",
                        1,
                    ) {
                        bindString(0, file.absolutePath)
                    }
                    FileInputStream(file).use {
                        with(it) {
                            copyTo(zipOutputStream)
                        }
                    }
                    zipOutputStream.closeEntry()
                    file.delete()
                    systemGraphCache.graphs().forEach {
                        val bindings = it as SystemBindings
                        zipOutputStream.putNextEntry(ZipEntry("databases/projectkafka-${bindings.system.id}.db"))
                        val file = File.createTempFile("export_", ".db", context.cacheDir)
                        bindings.driver.execute(
                            null,
                            "VACUUM main INTO ?",
                            1,
                        ) {
                            bindString(0, file.absolutePath)
                        }
                        FileInputStream(file).use { stream ->
                            with(stream) {
                                copyTo(zipOutputStream)
                            }
                        }
                        zipOutputStream.closeEntry()
                        file.delete()
                    }

                    val assets = File(context.filesDir, "assets")
                    val files = (assets.listFiles { it.isFile } ?: emptyArray())
                    files.forEach {
                        zipOutputStream.putNextEntry(ZipEntry("assets/${it.name}"))
                        FileInputStream(it).use { stream ->
                            with(stream) {
                                copyTo(zipOutputStream)
                            }
                        }
                        zipOutputStream.closeEntry()
                    }
                }
            }
        }
}
