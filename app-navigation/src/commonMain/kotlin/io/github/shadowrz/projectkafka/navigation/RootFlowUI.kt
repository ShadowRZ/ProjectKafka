package io.github.shadowrz.projectkafka.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.ChildStack
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Surface

@OptIn(ExperimentalDecomposeApi::class)
@Composable
@HanekokoroInject(AppScope::class)
internal fun RootFlowUI(
    component: RootFlowComponent,
    modifier: Modifier = Modifier,
) {
    val useSystemFont by component.appPreferencesStore.useSystemFont().collectAsState(false)

    KafkaTheme(useSystemFont = useSystemFont) {
        Surface(modifier = modifier) {
            ChildStack(stack = component.childStack) {
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
