package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.lifecycle.lifecycleScope
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.RestoreBindings
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.SystemBindings
import io.github.shadowrz.projectkafka.libraries.architecture.bindings
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.di.ResetDependencyGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.Path.Companion.toPath
import timber.log.Timber

class RestoreDataActivity : ComponentActivity() {
    private lateinit var bindings: RestoreBindings
    private val logger = LoggerTag("DataRestore", LoggerTag.Root)

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = bindings()

        if (savedInstanceState == null) {
            lifecycleScope.launch(context = Dispatchers.IO) {
                startRestore()
                val intent = packageManager.getLaunchIntentForPackage(packageName)!!
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finishAndRemoveTask()
            }
        }
    }

    private fun startRestore() {
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

        val restoreDir = intent.getStringExtra(RESTORE_DIR_KEY)

        if (restoreDir != null) {
            Timber.tag(logger.value).d("Restoring from backup extracted at %s", restoreDir)
            restoreData(this, restoreDir.toPath(normalize = true))
        }

        (applicationContext as ResetDependencyGraph).resetGraph()
    }

    private fun restoreData(
        context: Context,
        restoreDir: Path,
    ) {
        Timber.tag(logger.value).d("Clearing existing databases")
        context.databaseList().forEach {
            val result = context.deleteDatabase(it)
            Timber.tag(logger.value).d("Deleted database %s, result: %s", it, result)
        }

        bindings.fileSystem
            .listRecursively(
                restoreDir / "databases",
                followSymlinks = false,
            ).filter {
                // Don't process any symlinks
                bindings.fileSystem.metadata(it).symlinkTarget == null
            }.forEach {
                val output = context.getDatabasePath(it.relativeTo(restoreDir / "databases").name)
                Timber.tag(logger.value).d("Restoring database file %s to %s", it.name, output.absolutePath)
                bindings.fileSystem.atomicMove(it, output.toOkioPath(normalize = true))
            }

        Timber.tag(logger.value).d("Replacing existing assets")
        bindings.fileSystem.deleteRecursively(context.filesDir.toOkioPath() / "assets")
        if (bindings.fileSystem.exists(restoreDir.resolve("assets"))) {
            bindings.fileSystem.atomicMove(restoreDir.resolve("assets"), context.filesDir.toOkioPath() / "assets")
        }

        Timber.tag(logger.value).d("Clearing backup extracted at %s", restoreDir.toString())
        bindings.fileSystem.deleteRecursively(restoreDir)
    }

    companion object {
        const val RESTORE_DIR_KEY = "io.github.shadowrz.projectkafka.features.datamanage.impl.RESTORE_DIR"
    }
}
