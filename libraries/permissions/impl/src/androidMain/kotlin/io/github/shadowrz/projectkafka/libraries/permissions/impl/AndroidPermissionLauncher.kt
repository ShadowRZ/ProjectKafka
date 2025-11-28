package io.github.shadowrz.projectkafka.libraries.permissions.impl

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.shouldShowRationale
import io.github.shadowrz.projectkafka.libraries.permissions.api.PermissionLauncher
import io.github.shadowrz.projectkafka.libraries.permissions.api.PermissionStatus
import com.google.accompanist.permissions.PermissionStatus as AccompanistPermissionStatus

@OptIn(ExperimentalPermissionsApi::class)
class AndroidPermissionLauncher(
    val state: PermissionState,
) : PermissionLauncher {
    override fun launchPermissionRequest() {
        state.launchPermissionRequest()
    }

    override val status: PermissionStatus
        get() =
            when (state.status) {
                is AccompanistPermissionStatus.Denied -> {
                    PermissionStatus.Rejected(
                        shouldShowRationale = state.status.shouldShowRationale,
                    )
                }

                AccompanistPermissionStatus.Granted -> {
                    PermissionStatus.Granted
                }
            }
}
