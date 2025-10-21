package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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
    private val appCoroutineScope: CoroutineScope,
) : Presenter<DataManageState> {
    @Composable
    override fun present(): DataManageState {
        val snackbarHostState = remember { SnackbarHostState() }
        val exportedMessage = stringResource(R.string.datamanage_export_completed)

        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.CreateDocument("application/zip"),
        ) { uri ->
            uri?.let {
                appCoroutineScope.launch {
                    zipPackager.packageZip(it)
                    snackbarHostState.showSnackbar(exportedMessage)
                }
            }
        }

        return DataManageState(
            snackbarHostState = snackbarHostState,
        ) {
            when (it) {
                DataManageEvents.Backup -> launcher.launch("projectkafka.zip")
                DataManageEvents.Restore -> {}
            }
        }
    }
}
