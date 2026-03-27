package io.github.shadowrz.projectkafka.features.ftue.impl

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.MobileLockOrientation
import io.github.shadowrz.projectkafka.designsystem.ScreenOrientation
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationUI
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Composable
@HanekokoroInject.ContributesRenderer(SystemScope::class)
internal fun FtueUI(
    component: FtueComponent,
    modifier: Modifier = Modifier,
) {
    val step by component.step.subscribeAsState()

    Crossfade(
        step.child?.instance,
        modifier = modifier,
    ) {
        it?.let {
            when (it) {
                FtueComponent.Resolved.Root -> {
                    LoadingIndicator(
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                    )
                }

                is FtueComponent.Resolved.Notifications -> {
                    val state = it.component.presenter.present()

                    NotificationUI(state = state)
                }
            }
        }
    }

    MobileLockOrientation(orientation = ScreenOrientation.PORTRAIT)
}
