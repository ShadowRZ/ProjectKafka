package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimationScope
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
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
@NonRestartableComposable
fun <C : Any, T : Any> ChildStack(
    stack: ChildStack<C, T>,
    backHandler: BackHandler,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    animation: StackAnimation<C, T>? = stackAnimation(
        animator = fade() + slide(),
        predictiveBackParams = {
            defaultPredictiveBackParams(
                enabled = PLATFORM_SUPPORTS_PREDICTIVE_BACK,
                backHandler = backHandler,
                onBack = onBack,
            )
        },
    ),
    content: @Composable StackAnimationScope.(child: Child.Created<C, T>) -> Unit,
) {
    ChildStack(
        stack = stack,
        modifier = modifier,
        animation = animation,
        content = content,
    )
}

/**
 * @param backHandler A [BackHandler] for observing back events, usually taken from the
 * corresponding child [ComponentContext][com.arkivanov.decompose.ComponentContext].
 * @param onBack a callback to be called when the back gesture is confirmed (finished),
 * it should usually call [StackNavigator#pop][com.arkivanov.decompose.router.stack.pop].
 */
@OptIn(ExperimentalDecomposeApi::class)
@Composable
@NonRestartableComposable
fun <C : Any, T : Any> ChildStack(
    stack: Value<ChildStack<C, T>>,
    backHandler: BackHandler,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    animation: StackAnimation<C, T>? = stackAnimation(
        animator = fade() + slide(),
        predictiveBackParams = {
            defaultPredictiveBackParams(
                enabled = PLATFORM_SUPPORTS_PREDICTIVE_BACK,
                backHandler = backHandler,
                onBack = onBack,
            )
        },
    ),
    content: @Composable StackAnimationScope.(child: Child.Created<C, T>) -> Unit,
) {
    ChildStack(
        stack = stack,
        modifier = modifier,
        animation = animation,
        content = content,
    )
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
@NonRestartableComposable
fun <C : Any, T : Any> ChildStack(
    stack: ChildStack<C, T>,
    modifier: Modifier = Modifier,
    animation: StackAnimation<C, T>? = stackAnimation(animator = fade()),
    content: @Composable StackAnimationScope.(child: Child.Created<C, T>) -> Unit,
) {
    com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack(
        stack = stack,
        modifier = modifier,
        animation = animation,
        content = content,
    )
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
@NonRestartableComposable
fun <C : Any, T : Any> ChildStack(
    stack: Value<ChildStack<C, T>>,
    modifier: Modifier = Modifier,
    animation: StackAnimation<C, T>? = stackAnimation(animator = fade()),
    content: @Composable StackAnimationScope.(child: Child.Created<C, T>) -> Unit,
) {
    com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack(
        stack = stack,
        modifier = modifier,
        animation = animation,
        content = content,
    )
}
