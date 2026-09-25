package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.PlatformFile
import okio.Path

internal interface RestoreLauncher {
    suspend fun restore(
        file: PlatformFile,
        filesDir: Path,
        databaseDir: Path,
        restoreDir: Path,
    )
}

@Composable internal expect fun rememberRestoreLauncher(): RestoreLauncher
