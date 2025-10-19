package io.github.shadowrz.projectkafka.libraries.permissions.impl

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.permissions.api.NotificationPermissionProvider
import io.github.shadowrz.projectkafka.libraries.permissions.api.PermissionLauncher
import io.github.shadowrz.projectkafka.libraries.permissions.api.PermissionProvider
import kotlinx.coroutines.flow.Flow

@Inject
@ContributesBinding(AppScope::class)
class AndroidNotificationPermissionProvider(
    @ApplicationContext private val context: Context,
    private val permissionProvider: PermissionProvider,
    private val permissionStore: PermissionStore,
) : NotificationPermissionProvider {
    @ChecksSdkIntAtLeast(Build.VERSION_CODES.TIRAMISU)
    override fun canRequestPermission(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    override fun rememberPermissionLauncher(onPermissionResult: (Boolean) -> Unit): PermissionLauncher =
        permissionProvider.rememberPermissionLauncher(Manifest.permission.POST_NOTIFICATIONS, onPermissionResult)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun hasGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("InlinedApi")
    override fun hasRequested(): Flow<Boolean> = permissionStore.hasRequested(Manifest.permission.POST_NOTIFICATIONS)

    @SuppressLint("InlinedApi")
    override fun hasRejected(): Flow<Boolean> = permissionStore.hasRejected(Manifest.permission.POST_NOTIFICATIONS)

    @SuppressLint("InlinedApi")
    override suspend fun setHasRejected(rejected: Boolean) {
        permissionStore.setHasRejected(Manifest.permission.POST_NOTIFICATIONS, rejected)
    }
}
