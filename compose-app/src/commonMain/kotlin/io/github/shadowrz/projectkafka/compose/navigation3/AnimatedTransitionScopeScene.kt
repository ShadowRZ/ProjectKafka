package io.github.shadowrz.projectkafka.compose.navigation3

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.slack.circuit.sharedelements.ProvideAnimatedTransitionScope
import com.slack.circuit.sharedelements.SharedElementTransitionScope

class AnimatedTransitionScopeScene<T : Any>(scene: Scene<T>) : Scene<T> by scene {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override val content =
        @Composable {
            ProvideAnimatedTransitionScope(
                animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
                animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                content = { scene.content() },
            )
        }
}
