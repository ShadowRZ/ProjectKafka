package io.github.shadowrz.projectkafka.libraries.permissions.api

sealed interface PermissionStatus {
    data object Granted : PermissionStatus

    data class Rejected(
        val shouldShowRationale: Boolean,
    ) : PermissionStatus
}
