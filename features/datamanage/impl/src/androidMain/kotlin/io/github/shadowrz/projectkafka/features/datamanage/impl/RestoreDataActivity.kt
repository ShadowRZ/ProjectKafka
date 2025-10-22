package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.RestoreBindings
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.SystemBindings
import io.github.shadowrz.projectkafka.libraries.architecture.bindings
import io.github.shadowrz.projectkafka.libraries.components.theme.ProjectKafkaTheme
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.di.ResetDependencyGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class RestoreDataActivity : ComponentActivity() {
    private var bindings: RestoreBindings? = null
    private val logger = LoggerTag("DataRestore", LoggerTag.Root)

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        bindings = bindings()

        setContent {
            ProjectKafkaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoadingIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
                }
            }
        }

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
        bindings?.apply {
            // Cancel all coroutine scopes, and closes driver connections.
            coroutineScope.cancel()
            driver.close()
            systemGraphCache.graphs().forEach {
                val bindings = it as SystemBindings
                bindings.driver.close()
            }
            systemGraphCache.clear()
        }

        val restoreDir = bindings?.sharedPreferences?.getString(RESTORE_DIR_KEY, null)
        bindings?.sharedPreferences?.edit(commit = true) {
            remove(RESTORE_DIR_KEY)
        }

        bindings = null

        if (restoreDir != null) {
            Timber.tag(logger.value).d("Restoring from backup extracted at %s", restoreDir)
            restoreData(this, File(restoreDir))
        }

        (applicationContext as ResetDependencyGraph).resetGraph()
    }

    private fun restoreData(
        context: Context,
        restoreDir: File,
    ) {
        Timber.tag(logger.value).d("Clearing existing databases")
        context.databaseList().forEach {
            val result = context.deleteDatabase(it)
            Timber.tag(logger.value).d("Deleted database %s, result: %s", it, result)
        }

        restoreDir
            .resolve("databases")
            .listFiles { it.extension == "db" }
            ?.forEach {
                val output = context.getDatabasePath(it.name)
                Timber.tag(logger.value).d("Restoring database file %s to %s", it.name, output.absolutePath)
                Files.move(it.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }

        Timber.tag(logger.value).d("Clearing existing assets")
        context.filesDir.resolve("assets").apply {
            deleteRecursively()
            mkdir()
        }

        restoreDir.resolve("assets").listFiles()?.forEach {
            val output = context.filesDir.resolve("assets/${it.name}")
            Timber.tag(logger.value).d("Restoring asset file %s to %s", it.name, output.absolutePath)
            Files.move(it.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }

        Timber.tag(logger.value).d("Clearing backup extracted at %s", restoreDir.absolutePath)
        restoreDir.deleteRecursively()
    }

    companion object {
        const val RESTORE_DIR_KEY = "io.github.shadowrz.projectkafka.features.datamanage.impl.RESTORE_DIR"
    }
}
