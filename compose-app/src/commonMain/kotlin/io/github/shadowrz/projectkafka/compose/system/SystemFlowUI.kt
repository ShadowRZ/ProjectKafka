package io.github.shadowrz.projectkafka.compose.system

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.slack.circuit.sharedelements.ProvideAnimatedTransitionScope
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroContent
import io.github.shadowrz.projectkafka.designsystem.ChildStack
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.Surface
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@OptIn(
    ExperimentalDecomposeApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
@HanekokoroInject(SystemScope::class)
internal fun SystemFlowUI(
    component: SystemFlowComponent,
    modifier: Modifier = Modifier,
) {
    MaterialTheme {
        Surface(modifier = modifier) {
            ChildStack(
                stack = component.childStack,
                backHandler = component.backHandler,
                onBack = component::onBack,
            ) { child ->
                ProvideAnimatedTransitionScope(
                    animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
                    animatedVisibilityScope = this,
                ) {
                    when (val resolved = child.instance) {
                        SystemFlowComponent.Resolved.Placeholder -> {
                            LoadingIndicator(
                                modifier = Modifier.fillMaxSize().wrapContentSize(),
                            )
                        }

                        is SystemFlowComponent.Resolved.HasComponent -> {
                            HanekokoroContent(
                                resolved.component,
                            )
                        }
                    }
                }
            }
        }
    }
}
