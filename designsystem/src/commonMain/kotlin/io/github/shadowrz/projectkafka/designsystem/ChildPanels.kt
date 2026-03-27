package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanelsAnimators
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanelsLayout
import com.arkivanov.decompose.extensions.compose.experimental.panels.HorizontalChildPanelsLayout
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimationScope
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandler
import io.github.shadowrz.projectkafka.designsystem.internal.PLATFORM_SUPPORTS_PREDICTIVE_BACK
import io.github.shadowrz.projectkafka.designsystem.internal.defaultPredictiveBackParams

/**
 * @param backHandler A [BackHandler] for observing back events, usually taken from the
 * corresponding child [ComponentContext][com.arkivanov.decompose.ComponentContext].
 * @param onBack a callback to be called when the back gesture is confirmed (finished),
 * it should usually call [StackNavigator#pop][com.arkivanov.decompose.router.stack.pop].
 */
@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun <MC : Any, MT : Any, DC : Any, DT : Any> ChildPanels(
    panels: Value<ChildPanels<MC, MT, DC, DT, Nothing, Nothing>>,
    mainChild: @Composable StackAnimationScope.(Child.Created<MC, MT>) -> Unit,
    detailsChild: @Composable StackAnimationScope.(Child.Created<DC, DT>) -> Unit,
    backHandler: BackHandler,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    secondPanelPlaceholder: @Composable StackAnimationScope.() -> Unit = {},
    layout: ChildPanelsLayout = remember { HorizontalChildPanelsLayout() },
    animators: ChildPanelsAnimators<MC, MT, DC, DT, Nothing, Nothing> = remember {
        ChildPanelsAnimators(
            single = fade() + slide(),
            dual = fade() to fade(),
        )
    },
    predictiveBackParams: (ChildPanels<MC, MT, DC, DT, Nothing, Nothing>) -> PredictiveBackParams? = {
        defaultPredictiveBackParams(
            enabled = PLATFORM_SUPPORTS_PREDICTIVE_BACK,
            backHandler = backHandler,
            onBack = onBack,
        )
    },
) {
    com.arkivanov.decompose.extensions.compose.experimental.panels
        .ChildPanels(
            modifier = modifier,
            panels = panels,
            mainChild = mainChild,
            detailsChild = detailsChild,
            secondPanelPlaceholder = secondPanelPlaceholder,
            layout = layout,
            animators = animators,
            predictiveBackParams = predictiveBackParams,
        )
}
