package io.github.shadowrz.projectkafka.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.components.theme.ProjectKafkaTheme

@SingleIn(AppScope::class)
@Inject
@ContributesIntoMap(
    AppScope::class,
    binding = binding<ComponentUI<*>>(),
)
@ComponentKey(RootFlowComponent::class)
class RootFlowUI : ComponentUI<RootFlowComponent> {
    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun Content(
        component: RootFlowComponent,
        modifier: Modifier,
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
}
