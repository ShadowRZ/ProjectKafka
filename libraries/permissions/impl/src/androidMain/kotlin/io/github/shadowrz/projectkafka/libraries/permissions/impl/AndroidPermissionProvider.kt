package io.github.shadowrz.projectkafka.libraries.permissions.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalInspectionMode
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.permissions.api.PermissionLauncher
import io.github.shadowrz.projectkafka.libraries.permissions.api.PermissionProvider
import kotlinx.coroutines.launch

@Inject
@ContributesBinding(AppScope::class)
class AndroidPermissionProvider(
    private val permissionStore: PermissionStore,
) : PermissionProvider {
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun rememberPermissionLauncher(
        permission: String,
        onPermissionResult: (Boolean) -> Unit,
    ): PermissionLauncher {
        if (LocalInspectionMode.current) return PermissionLauncher.NoOp

        val scope = rememberCoroutineScope()

        val state =
            rememberPermissionState(
                permission = permission,
                onPermissionResult = {
                    onPermissionResult(it)
                    scope.launch {
                        permissionStore.setHasRequested(permission, true)
                        permissionStore.setHasRejected(permission, !it)
                    }
                },
            )

        LaunchedEffect(state.status) {
        }

        return AndroidPermissionLauncher(state = state)
    }
}
