package io.github.shadowrz.projectkafka.navigation.system

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.slack.circuit.sharedelements.ProvideAnimatedTransitionScope
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroContent
import io.github.shadowrz.projectkafka.libraries.components.PLATFORM_SUPPORTS_PREDICTIVE_BACK
import io.github.shadowrz.projectkafka.libraries.components.predictiveback.defaultPredictiveBackParams
import io.github.shadowrz.projectkafka.libraries.components.theme.ProjectKafkaTheme
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@OptIn(
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalDecomposeApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
@HanekokoroInject(SystemScope::class)
internal fun SystemFlowUI(
    component: SystemFlowComponent,
    modifier: Modifier = Modifier,
) {
    ProjectKafkaTheme {
        Surface(modifier = modifier) {
            ChildStack(
                stack = component.childStack,
                animation =
                    stackAnimation(
                        selector = ::animationSelector,
                        predictiveBackParams = {
                            defaultPredictiveBackParams(
                                enabled = PLATFORM_SUPPORTS_PREDICTIVE_BACK,
                                backHandler = component.backHandler,
                                onBack = component::onBack,
                            )
                        },
                    ),
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
