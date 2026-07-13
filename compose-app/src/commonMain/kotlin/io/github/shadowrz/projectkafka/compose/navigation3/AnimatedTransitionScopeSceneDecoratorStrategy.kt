package io.github.shadowrz.projectkafka.compose.navigation3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneDecoratorStrategy
import androidx.navigation3.scene.SceneDecoratorStrategyScope

@Composable
fun <T : Any> rememberAnimatedTransitionScopeSceneDecoratorStrategy(): AnimatedTransitionScopeSceneDecoratorStrategy<T> {
    return remember { AnimatedTransitionScopeSceneDecoratorStrategy() }
}

class AnimatedTransitionScopeSceneDecoratorStrategy<T : Any> : SceneDecoratorStrategy<T> {
    override fun SceneDecoratorStrategyScope<T>.decorateScene(scene: Scene<T>): Scene<T> {
        return AnimatedTransitionScopeScene(scene)
    }
}
