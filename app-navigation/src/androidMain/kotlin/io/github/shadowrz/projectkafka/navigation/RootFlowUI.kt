package io.github.shadowrz.projectkafka.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.components.theme.ProjectKafkaTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
@ContributesComponent(AppScope::class)
internal fun RootFlowUI(
    component: RootFlowComponent,
    modifier: Modifier = Modifier,
) {
    ProjectKafkaTheme {
        Surface(modifier = modifier) {
            ChildStack(
                stack = component.childStack,
                animation = stackAnimation(fade()),
            ) {
                when (val child = it.instance) {
                    RootFlowComponent.Resolved.SplashScreen -> {}
                    is RootFlowComponent.Resolved.NoSystemFlow ->
                        NoSystemFlowUI(child.component)

                    is RootFlowComponent.Resolved.SystemFlow -> {
                        SystemFlowAppScopeUI(child.component)
                    }
                }
            }
        }
    }
}
