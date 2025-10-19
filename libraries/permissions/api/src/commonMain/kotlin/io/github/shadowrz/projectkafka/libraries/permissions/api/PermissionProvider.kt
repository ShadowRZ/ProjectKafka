package io.github.shadowrz.projectkafka.libraries.permissions.api

import androidx.compose.runtime.Composable

interface PermissionProvider {
    @Composable
    fun rememberPermissionLauncher(
        permission: String,
        onPermissionResult: (Boolean) -> Unit = {},
    ): PermissionLauncher
}
