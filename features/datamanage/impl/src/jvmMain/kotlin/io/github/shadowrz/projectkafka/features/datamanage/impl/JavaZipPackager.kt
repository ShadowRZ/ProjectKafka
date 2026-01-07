package io.github.shadowrz.projectkafka.features.datamanage.impl

import app.cash.sqldelight.db.SqlDriver
import com.eygraber.uri.Uri
import com.eygraber.uri.toURI
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.SystemBindings
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import io.github.shadowrz.projectkafka.libraries.systemgraph.SystemGraphCache
import io.github.shadowrz.projectkafka.libraries.zipwriter.directory
import io.github.shadowrz.projectkafka.libraries.zipwriter.file
import io.github.shadowrz.projectkafka.libraries.zipwriter.writeZip
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.sink
import kotlin.io.path.createTempFile
import kotlin.io.path.toPath

@Inject
@ContributesBinding(AppScope::class)
class JavaZipPackager(
    @CacheDirectory private val cacheDir: Path,
    @FilesDirectory private val filesDir: Path,
    @ForScope(AppScope::class) private val driver: SqlDriver,
    private val systemGraphCache: SystemGraphCache,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val fileSystem: FileSystem,
) : ZipPackager {
    override suspend fun packageZip(output: Uri): Unit =
        withContext(coroutineDispatchers.io) {
            output.toURI().toPath().toAbsolutePath().toFile().sink().writeZip {
                val file = createTempFile(cacheDir.toNioPath(), "export_", ".db").toFile()
                driver.exportTo(file)
                file("databases/projectkafka.db", file)
                file.delete()
                systemGraphCache.graphs().forEach {
                    val bindings = it as SystemBindings
                    val file = createTempFile(cacheDir.toNioPath(), "export_", ".db").toFile()
                    bindings.driver.exportTo(file)
                    file("databases/projectkafka-${bindings.system.id}.db", file)
                    file.delete()
                }

                directory("assets", filesDir / "assets", fileSystem = fileSystem)
            }
        }
}
