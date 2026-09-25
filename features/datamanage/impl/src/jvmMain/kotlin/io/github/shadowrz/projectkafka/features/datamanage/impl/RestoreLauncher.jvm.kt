package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.PlatformFile
import okio.Path

@Composable
internal actual fun rememberRestoreLauncher(): RestoreLauncher {
    return object : RestoreLauncher {
        override suspend fun restore(
            file: PlatformFile,
            filesDir: Path,
            databaseDir: Path,
            restoreDir: Path,
        ) = Unit
    }
}
