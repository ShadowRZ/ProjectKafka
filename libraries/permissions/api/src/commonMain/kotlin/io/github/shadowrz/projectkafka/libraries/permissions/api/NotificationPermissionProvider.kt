package io.github.shadowrz.projectkafka.libraries.permissions.api

import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

interface NotificationPermissionProvider {
    @ChecksSdkIntAtLeast(api = 33)
    fun canRequestPermission(): Boolean

    @RequiresApi(api = 33)
    @Composable
    fun rememberPermissionLauncher(onPermissionResult: (Boolean) -> Unit = {}): PermissionLauncher

    fun hasGranted(): Boolean

    fun hasRequested(): Flow<Boolean>

    fun hasRejected(): Flow<Boolean>

    suspend fun setHasRejected(rejected: Boolean)
}
