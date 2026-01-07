package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.app.ActivityManager
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import com.eygraber.uri.toKmpUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.RestoreBindings
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner
import io.github.shadowrz.projectkafka.libraries.di.ResetDependencyGraph
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.shadowrz.projectkafka.libraries.di.annotations.DatabaseDirectory
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Path
import okio.Path.Companion.toPath
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.datamanage.impl.generated.resources.Res
import projectkafka.features.datamanage.impl.generated.resources.datamanage_export_completed

@Inject
@ContributesBinding(AppScope::class)
actual class DataManagePresenter(
    @FilesDirectory private val filesDir: Path,
    @DatabaseDirectory private val databaseDir: Path,
    private val zipPackager: ZipPackager,
    private val zipValidator: ZipValidator,
    private val appCoroutineScope: CoroutineScope,
    private val coroutineDispatchers: CoroutineDispatchers,
) : Presenter<DataManageState> {
    @Composable
    override fun present(): DataManageState {
        val snackbarHostState = remember { SnackbarHostState() }
        val exportedMessage = stringResource(Res.string.datamanage_export_completed)

        val context = LocalContext.current
        val activity = requireNotNull(LocalActivity.current)

        val backupLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.CreateDocument("application/zip"),
        ) { uri ->
            uri?.let {
                appCoroutineScope.launch {
                    zipPackager.packageZip(it.toKmpUri())
                    snackbarHostState.showSnackbar(exportedMessage)
                }
            }
        }

        val restoreLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument(),
        ) { uri ->
            uri?.let {
                appCoroutineScope.launch {
                    when (val result = zipValidator.unpackAndValidateZip(it.toKmpUri())) {
                        ZipValidator.Result.Invalid -> {
                            // TODO: Should report errors.
                        }

                        is ZipValidator.Result.Ok -> {
                            withContext(coroutineDispatchers.io + NonCancellable) {
                                restoreData(
                                    bindings = (context.applicationContext as DependencyGraphOwner).graph as RestoreBindings,
                                    filesDir = filesDir,
                                    databaseDir = databaseDir,
                                    restoreDir = result.unpacked,
                                ) {
                                    activity.apply {
                                        (applicationContext as ResetDependencyGraph).resetGraph()
                                        getSystemService<ActivityManager>()
                                            ?.appTasks
                                            ?.filter { task ->
                                                task.taskInfo.baseActivity?.className != "io.github.shadowrz.projectkafka.MainActivity"
                                            }.orEmpty()
                                            .forEach { task ->
                                                task.finishAndRemoveTask()
                                            }
                                        withContext(coroutineDispatchers.main) {
                                            recreate()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return DataManageState(
            snackbarHostState = snackbarHostState,
        ) {
            when (it) {
                DataManageEvents.Backup -> backupLauncher.launch("projectkafka.zip")
                DataManageEvents.Restore -> restoreLauncher.launch(arrayOf("application/zip"))
            }
        }
    }
}
