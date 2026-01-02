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
import io.github.shadowrz.projectkafka.libraries.systemgraph.SystemGraphCache
import io.github.shadowrz.projectkafka.libraries.zipwriter.directory
import io.github.shadowrz.projectkafka.libraries.zipwriter.file
import io.github.shadowrz.projectkafka.libraries.zipwriter.writeZip
import kotlinx.coroutines.withContext
import java.io.File

@Inject
class ZipPackager(
    @ApplicationContext private val context: Context,
    @ForScope(AppScope::class) private val driver: SqlDriver,
    private val systemGraphCache: SystemGraphCache,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun packageZip(output: Uri): Unit =
        withContext(coroutineDispatchers.io) {
            context.contentResolver.openOutputStream(output)?.use { stream ->
                stream.writeZip {
                    val file = File.createTempFile("export_", ".db", context.cacheDir)
                    driver.exportTo(file)
                    file("databases/projectkafka.db", file)
                    file.delete()
                    systemGraphCache.graphs().forEach {
                        val bindings = it as SystemBindings
                        val file = File.createTempFile("export_", ".db", context.cacheDir)
                        bindings.driver.exportTo(file)
                        file("databases/projectkafka-${bindings.system.id}.db", file)
                        file.delete()
                    }

                    directory("assets", File(context.filesDir, "assets"))
                }
            }
        }
}
