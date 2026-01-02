package io.github.shadowrz.projectkafka.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroContent
import io.github.shadowrz.projectkafka.libraries.components.MobileLockOrientation
import io.github.shadowrz.projectkafka.libraries.components.PLATFORM_SUPPORTS_PREDICTIVE_BACK
import io.github.shadowrz.projectkafka.libraries.components.ScreenOrientation
import io.github.shadowrz.projectkafka.libraries.components.predictiveback.defaultPredictiveBackParams

@OptIn(ExperimentalDecomposeApi::class)
@Composable
internal fun NoSystemFlowUI(
    component: NoSystemFlowComponent,
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
        HanekokoroContent(
            component = it.instance,
        )
    }

    MobileLockOrientation(orientation = ScreenOrientation.PORTRAIT)
}
