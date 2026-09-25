package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.runtime.Composable
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.designsystem.Snackbar
import io.github.shadowrz.projectkafka.designsystem.show
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.annotations.DatabaseDirectory
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFileSaverLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Path
import projectkafka.features.datamanage.impl.generated.resources.Res
import projectkafka.features.datamanage.impl.generated.resources.datamanage_export_completed

@Inject
@ContributesBinding(AppScope::class)
class DataManagePresenter(
    @FilesDirectory private val filesDir: Path,
    @DatabaseDirectory private val databaseDir: Path,
    private val zipPackager: ZipPackager,
    private val zipValidator: ZipValidator,
    private val appCoroutineScope: CoroutineScope,
    private val coroutineDispatchers: CoroutineDispatchers,
) : Presenter<DataManageState> {
    @Composable
    override fun present(): DataManageState {
        val restoreZipLauncher = rememberRestoreLauncher()

        val backupLauncher =
            rememberFileSaverLauncher(dialogSettings = FileKitDialogSettings.createDefault()) { file ->
                file?.let {
                    appCoroutineScope.launch {
                        zipPackager.packageZip(it)
                        Snackbar.show(message = Res.string.datamanage_export_completed)
                    }
                }
            }

        val restoreLauncher =
            rememberFilePickerLauncher(type = FileKitType.File(extension = "zip")) { file ->
                file?.let {
                    appCoroutineScope.launch {
                        when (val result = zipValidator.unpackAndValidateZip(it)) {
                            ZipValidator.Result.Invalid -> {
                                // TODO: Should report errors.
                            }

                            is ZipValidator.Result.Ok -> {
                                withContext(coroutineDispatchers.io + NonCancellable) {
                                    restoreZipLauncher.restore(
                                        file = it,
                                        filesDir = filesDir,
                                        databaseDir = databaseDir,
                                        restoreDir = result.unpacked,
                                    )
                                }
                            }
                        }
                    }
                }
            }

        return DataManageState {
            when (it) {
                DataManageEvents.Backup -> backupLauncher.launch("projectkafka", defaultExtension = "zip")
                DataManageEvents.Restore -> restoreLauncher.launch()
            }
        }
    }
}
