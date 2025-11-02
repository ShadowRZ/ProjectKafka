package io.github.shadowrz.projectkafka.navigation.system

import androidx.compose.animation.core.tween
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.Direction

@OptIn(ExperimentalDecomposeApi::class)
fun <C : Any, T : Any> animationSelector(
    child: Child.Created<C, T>,
    otherChild: Child.Created<C, T>,
    direction: Direction,
    @Suppress("unused") isPredictiveBack: Boolean,
): StackAnimator? =
    when {
        child.configuration == SystemFlowComponent.NavTarget.Placeholder &&
            direction == Direction.EXIT_BACK
        -> fade(tween(0))
        otherChild.configuration == SystemFlowComponent.NavTarget.Placeholder &&
            direction == Direction.ENTER_FRONT
        -> fade(tween(0))
        child.configuration == SystemFlowComponent.NavTarget.Share &&
            direction == Direction.EXIT_BACK
        -> fade(tween(0))
        otherChild.configuration == SystemFlowComponent.NavTarget.Share &&
            direction == Direction.ENTER_FRONT
        -> fade(tween(0))
        else -> fade() + slide()
    }
