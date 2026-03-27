package io.github.shadowrz.projectkafka.features.preferences.impl

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.ChildStack
import io.github.shadowrz.projectkafka.features.preferences.impl.root.PreferencesRootUI

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
        backHandler = component.backHandler,
        onBack = component::onBack,
    ) {
        when (val child = it.instance) {
            is PreferencesComponent.Resolved.Root -> PreferencesRootUI(
                component = child.component,
            )
        }
    }
}
