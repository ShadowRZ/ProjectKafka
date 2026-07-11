package io.github.shadowrz.projectkafka.features.ftue.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.MobileLockOrientation
import io.github.shadowrz.projectkafka.designsystem.ScreenOrientation
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationComponent
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationUI
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.launch

@Composable
@HanekokoroInject.ContributesRenderer(SystemScope::class)
internal fun FtueUI(
    component: FtueComponent,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    val backStack =
        rememberNavBackStack(
            FtueComponent.NavTarget.CONFIG,
            FtueComponent.NavTarget.Root,
        )

    suspend fun moveToNextStepIfNeeded() {
        when (component.ftueService.nextStep()) {
            FtueStep.NotificationOptIn -> backStack[0] = FtueComponent.NavTarget.Notifications
            null -> component.ftueService.updateState()
        }
    }

    LaunchedEffect(Unit) {
        moveToNextStepIfNeeded()
    }

    NavDisplay(
        backStack = backStack,
        onBack = {},
        modifier = modifier,
        entryProvider =
            entryProvider {
                entry<FtueComponent.NavTarget.Root> {
                    LoadingIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
                }
                entry<FtueComponent.NavTarget.Notifications> {
                    val presenter = remember {
                        component.notificationPresenterFactory.create(
                            object : NotificationComponent.Callback {
                                override fun onDone() {
                                    coroutineScope.launch {
                                        moveToNextStepIfNeeded()
                                    }
                                }
                            }
                        )
                    }
                    val state = presenter.present()

                    NotificationUI(state = state)
                }
            },
    )

    MobileLockOrientation(orientation = ScreenOrientation.PORTRAIT)
}
