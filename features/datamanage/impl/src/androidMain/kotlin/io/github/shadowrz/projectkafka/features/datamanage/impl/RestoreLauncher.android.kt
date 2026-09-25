package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.app.Activity
import android.app.ActivityManager
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.getSystemService
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.RestoreBindings
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner
import io.github.shadowrz.projectkafka.libraries.di.ResetDependencyGraph
import io.github.vinceglb.filekit.PlatformFile
import okio.Path

@Composable
internal actual fun rememberRestoreLauncher(): RestoreLauncher {
    val activity = requireNotNull(LocalActivity.current)

    return remember(activity) { AndroidRestoreLauncher(activity) }
}

internal class AndroidRestoreLauncher(val activity: Activity) : RestoreLauncher {
    override suspend fun restore(
        file: PlatformFile,
        filesDir: Path,
        databaseDir: Path,
        restoreDir: Path,
    ) {
        restoreData(
            bindings = (activity.applicationContext as DependencyGraphOwner).graph as RestoreBindings,
            filesDir = filesDir,
            databaseDir = databaseDir,
            restoreDir = restoreDir,
        ) {
            activity.apply {
                (applicationContext as ResetDependencyGraph).resetGraph()
                getSystemService<ActivityManager>()
                    ?.appTasks
                    ?.filter { task ->
                        task.taskInfo?.baseActivity?.className != "io.github.shadowrz.projectkafka.MainActivity"
                    }
                    .orEmpty()
                    .forEach { task ->
                        task.finishAndRemoveTask()
                    }
                runOnUiThread { recreate() }
            }
        }
    }
}
