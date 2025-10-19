package io.github.shadowrz.projectkafka.libraries.permissions.api

import androidx.compose.runtime.Stable

@Stable
interface PermissionLauncher {
    val status: PermissionStatus

    fun launchPermissionRequest()

    object NoOp : PermissionLauncher {
        override val status: PermissionStatus = PermissionStatus.Granted

        override fun launchPermissionRequest() {}
    }
}
