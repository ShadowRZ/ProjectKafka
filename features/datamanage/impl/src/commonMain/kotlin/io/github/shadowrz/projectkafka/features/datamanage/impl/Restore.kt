package io.github.shadowrz.projectkafka.features.datamanage.impl

import io.github.shadowrz.projectkafka.features.datamanage.impl.di.RestoreBindings
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.SystemBindings
import kotlinx.coroutines.cancel
import okio.Path

suspend fun restoreData(
    bindings: RestoreBindings,
    filesDir: Path,
    databaseDir: Path,
    restoreDir: Path,
    afterRestore: suspend () -> Unit,
) {
    bindings.apply {
        // Cancel all coroutine scopes, and closes driver connections.
        coroutineScope.cancel()
        driver.close()
        systemGraphCache.graphs().forEach {
            val bindings = it as SystemBindings
            bindings.driver.close()
        }
        systemGraphCache.clear()
    }

    bindings.fileSystem.apply {
        deleteRecursively(databaseDir)
        createDirectories(databaseDir)
    }

    bindings.fileSystem
        .listRecursively(
            restoreDir / "databases",
            followSymlinks = false,
        ).filter {
            // Don't process any symlinks
            bindings.fileSystem.metadata(it).symlinkTarget == null
        }.forEach {
            val output = databaseDir / it.relativeTo(restoreDir / "databases").name
            bindings.fileSystem.atomicMove(it, output)
        }

    bindings.fileSystem.deleteRecursively(filesDir / "assets")
    if (bindings.fileSystem.exists(restoreDir.resolve("assets"))) {
        bindings.fileSystem.atomicMove(restoreDir.resolve("assets"), filesDir / "assets")
    }

    bindings.fileSystem.deleteRecursively(restoreDir)

    afterRestore()
}
