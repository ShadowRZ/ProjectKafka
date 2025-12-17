package io.github.shadowrz.projectkafka.features.preferences.impl

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.features.preferences.impl.root.PreferencesRootUI
import io.github.shadowrz.projectkafka.libraries.components.PLATFORM_SUPPORTS_PREDICTIVE_BACK
import io.github.shadowrz.projectkafka.libraries.components.predictiveback.defaultPredictiveBackParams

@OptIn(
    ExperimentalDecomposeApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun PreferencesUI(
    component: PreferencesComponent,
    modifier: Modifier = Modifier,
) {
    ChildStack(
        modifier = modifier,
        stack = component.childStack,
        animation =
            stackAnimation(
                animator = fade() + slide(),
                predictiveBackParams = {
                    defaultPredictiveBackParams(
                        enabled = PLATFORM_SUPPORTS_PREDICTIVE_BACK,
                        backHandler = component.backHandler,
                        onBack = component::onBack,
                    )
                },
            ),
    ) {
        when (val child = it.instance) {
            is PreferencesComponent.Resolved.Root -> PreferencesRootUI(
                component = child.component,
            )
        }
    }
}
