package io.github.shadowrz.projectkafka.features.ftue.impl

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.MobileLockOrientation
import io.github.shadowrz.projectkafka.designsystem.ScreenOrientation
import io.github.shadowrz.projectkafka.features.ftue.api.FtueScreen
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationCallback
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationPresenter
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationUI
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.Navigator
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.launch

@Inject
@ContributesIntoSet(SystemScope::class)
class FtueNavEntryProvider(
    private val ftueService: DefaultFtueService,
    private val notificationPresenterFactory: NotificationPresenter.Factory,
) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry(navigator: Navigator, sharedTransitionScope: SharedTransitionScope) {
        entry<FtueScreen> {
            val coroutineScope = rememberCoroutineScope()

            val backStack =
                rememberNavBackStack(
                    FtueNavTarget.CONFIG,
                    FtueNavTarget.Root,
                )

            suspend fun moveToNextStepIfNeeded() {
                when (ftueService.nextStep()) {
                    FtueStep.NotificationOptIn -> backStack[0] = FtueNavTarget.Notifications
                    null -> ftueService.updateState()
                }
            }

            LaunchedEffect(Unit) {
                moveToNextStepIfNeeded()
            }

            NavDisplay(
                backStack = backStack,
                onBack = {},
                entryProvider =
                    entryProvider {
                        entry<FtueNavTarget.Root> {
                            LoadingIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
                        }
                        entry<FtueNavTarget.Notifications> {
                            val presenter = remember {
                                notificationPresenterFactory.create(
                                    object : NotificationCallback {
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
    }
}
