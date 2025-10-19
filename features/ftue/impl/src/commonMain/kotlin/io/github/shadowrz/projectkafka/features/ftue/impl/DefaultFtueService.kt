package io.github.shadowrz.projectkafka.features.ftue.impl

import androidx.annotation.VisibleForTesting
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.features.ftue.api.FtueService
import io.github.shadowrz.projectkafka.features.ftue.api.FtueState
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.permissions.api.NotificationPermissionProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Inject
@ContributesBinding(SystemScope::class)
@SingleIn(SystemScope::class)
class DefaultFtueService(
    @ForScope(SystemScope::class) systemCoroutineScope: CoroutineScope,
    private val notificationPermissionProvider: NotificationPermissionProvider,
) : FtueService {
    override val state: MutableStateFlow<FtueState> = MutableStateFlow(FtueState.Unknown)

    init {
        systemCoroutineScope.launch {
            updateState()
        }
    }

    suspend fun nextStep(current: FtueStep? = null): FtueStep? =
        when (current) {
            null -> if (shouldAskForNotificationOptIn()) FtueStep.NotificationOptIn else nextStep(FtueStep.NotificationOptIn)
            FtueStep.NotificationOptIn -> null
        }

    internal suspend fun updateState() {
        val nextStep = nextStep()
        state.value =
            when {
                nextStep == null -> FtueState.Complete
                else -> FtueState.Incomplete
            }
    }

    private suspend fun shouldAskForNotificationOptIn(): Boolean =
        if (notificationPermissionProvider.canRequestPermission()) {
            val hasGranted = notificationPermissionProvider.hasGranted()
            val hasRejected = notificationPermissionProvider.hasRejected().first()
            !hasGranted && !hasRejected
        } else {
            false
        }
}
