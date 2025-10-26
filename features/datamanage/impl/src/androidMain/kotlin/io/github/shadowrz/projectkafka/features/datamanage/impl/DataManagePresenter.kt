package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Inject
@ContributesBinding(AppScope::class)
actual class DataManagePresenter(
    private val zipPackager: ZipPackager,
    private val zipValidator: ZipValidator,
    private val appCoroutineScope: CoroutineScope,
) : Presenter<DataManageState> {
    @Composable
    override fun present(): DataManageState {
        val snackbarHostState = remember { SnackbarHostState() }
        val exportedMessage = stringResource(R.string.datamanage_export_completed)

        val activity = requireNotNull(LocalActivity.current)

        val backupLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.CreateDocument("application/zip"),
        ) { uri ->
            uri?.let {
                appCoroutineScope.launch {
                    zipPackager.packageZip(it)
                    snackbarHostState.showSnackbar(exportedMessage)
                }
            }
        }

        val restoreLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument(),
        ) { uri ->
            uri?.let {
                appCoroutineScope.launch {
                    val result = zipValidator.unpackAndValidateZip(it)
                    when (result) {
                        ZipValidator.Result.Invalid -> {}
                        is ZipValidator.Result.Ok -> {
                            val intent = Intent(activity, RestoreDataActivity::class.java)
                            intent.addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS,
                            )
                            intent.putExtra(RestoreDataActivity.RESTORE_DIR_KEY, result.unpacked.toString())
                            activity.startActivity(intent)
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
