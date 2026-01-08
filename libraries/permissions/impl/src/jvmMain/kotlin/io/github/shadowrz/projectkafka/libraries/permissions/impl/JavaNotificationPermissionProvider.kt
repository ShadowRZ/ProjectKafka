package io.github.shadowrz.projectkafka.libraries.permissions.impl

import androidx.compose.runtime.Composable
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.permissions.api.NotificationPermissionProvider
import io.github.shadowrz.projectkafka.libraries.permissions.api.PermissionLauncher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Inject
@ContributesBinding(AppScope::class)
class JavaNotificationPermissionProvider : NotificationPermissionProvider {
    override fun canRequestPermission(): Boolean = false

    @Composable
    override fun rememberPermissionLauncher(onPermissionResult: (Boolean) -> Unit): PermissionLauncher = PermissionLauncher.NoOp

    override fun hasGranted(): Boolean = true

    override fun hasRequested(): Flow<Boolean> = flowOf(true)

    override fun hasRejected(): Flow<Boolean> = flowOf(false)

    override suspend fun setHasRejected(rejected: Boolean) {
        // Nothing to do.
    }
}
