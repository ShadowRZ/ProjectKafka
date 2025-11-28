package io.github.shadowrz.projectkafka.features.ftue.impl

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.features.ftue.impl.notification.NotificationUI
import io.github.shadowrz.projectkafka.libraries.components.MobileLockOrientation
import io.github.shadowrz.projectkafka.libraries.components.ScreenOrientation
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@ContributesComponent(SystemScope::class)
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
