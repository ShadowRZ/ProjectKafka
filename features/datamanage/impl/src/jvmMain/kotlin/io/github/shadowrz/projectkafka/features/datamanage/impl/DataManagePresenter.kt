package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.datamanage.impl.generated.resources.Res
import projectkafka.features.datamanage.impl.generated.resources.datamanage_export_completed

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
        val exportedMessage = stringResource(Res.string.datamanage_export_completed)

        return DataManageState(
            snackbarHostState = snackbarHostState,
        ) {
            when (it) {
                DataManageEvents.Backup -> {}
                DataManageEvents.Restore -> {}
            }
        }
    }
}
