package io.github.shadowrz.projectkafka.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.libraries.components.theme.ProjectKafkaTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
@HanekokoroInject(AppScope::class)
internal fun RootFlowUI(
    component: RootFlowComponent,
    modifier: Modifier = Modifier,
) {
    val useSystemFont by component.appPreferencesStore.useSystemFont().collectAsState(false)

    ProjectKafkaTheme(useSystemFont = useSystemFont) {
        Surface(modifier = modifier) {
            ChildStack(
                stack = component.childStack,
                animation = stackAnimation(fade()),
            ) {
                when (val child = it.instance) {
                    RootFlowComponent.Resolved.SplashScreen -> {
                        // Nothing to render
                    }

                    is RootFlowComponent.Resolved.NoSystemFlow -> {
                        NoSystemFlowUI(child.component)
                    }

                    is RootFlowComponent.Resolved.SystemFlow -> {
                        SystemFlowAppScopeUI(child.component)
                    }
                }
            }
        }
    }
}
