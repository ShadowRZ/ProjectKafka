package io.github.shadowrz.projectkafka.features.ftue.impl.notification

import androidx.compose.runtime.Composable
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.permissions.api.NotificationPermissionProvider
import io.github.shadowrz.projectkafka.libraries.permissions.api.PermissionLauncher

@AssistedInject
class NotificationPresenter(
    @Assisted private val callback: NotificationComponent.Callback,
    private val notificationPermissionProvider: NotificationPermissionProvider,
) : Presenter<NotificationState> {
    @Composable
    override fun present(): NotificationState {
        val launcher =
            if (notificationPermissionProvider.canRequestPermission()) {
                notificationPermissionProvider.rememberPermissionLauncher {
                    callback.onDone()
                }
            } else {
                PermissionLauncher.NoOp
            }

        return NotificationState {
            when (it) {
                NotificationEvents.RequestNotification -> {
                    launcher.launchPermissionRequest()
                }

                NotificationEvents.SkipNotification -> {
                    callback.onDone()
                }
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(callback: NotificationComponent.Callback): NotificationPresenter
    }
}
