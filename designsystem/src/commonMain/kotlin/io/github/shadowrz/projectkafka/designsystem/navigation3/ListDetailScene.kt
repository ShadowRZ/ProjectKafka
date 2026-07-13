package io.github.shadowrz.projectkafka.designsystem.navigation3

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavMetadataKey
import androidx.navigation3.runtime.contains
import androidx.navigation3.runtime.get
import androidx.navigation3.runtime.metadata
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import com.slack.circuit.sharedelements.ProvideAnimatedTransitionScope
import com.slack.circuit.sharedelements.SharedElementTransitionScope

/** A [Scene] that displays a list and a detail [NavEntry] side-by-side in a 40/60 split. */
data class ListDetailScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val listEntry: NavEntry<T>,
    val detailEntry: NavEntry<T>?,
) : Scene<T> {
    override val entries: List<NavEntry<T>> = listOfNotNull(listEntry, detailEntry)
    @OptIn(ExperimentalSharedTransitionApi::class)
    override val content: @Composable (() -> Unit) = {
        Layout(
            modifier = Modifier.fillMaxSize(),
            content = {
                listEntry.Content()
                AnimatedContent(
                    targetState = detailEntry,
                    contentKey = { entry -> entry?.contentKey },
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                ) { entry ->
                    ProvideAnimatedTransitionScope(
                        animatedScope = SharedElementTransitionScope.AnimatedScope.Navigation,
                        animatedVisibilityScope = this,
                    ) {
                        entry?.Content() ?: listEntry.metadata[DetailPlacehilder]?.invoke()
                    }
                }
            },
        ) { measurables, constraints ->
            val w1 = (constraints.maxWidth.toFloat() * 0.5f).toInt()
            val w2 = constraints.maxWidth - w1
            val placeable1 = measurables[0].measure(constraints.copy(maxWidth = w1, minWidth = w1))
            val placeable2 = measurables[1].measure(constraints.copy(maxWidth = w2, minWidth = w2))

            layout(constraints.maxWidth, constraints.maxHeight) {
                placeable1.placeRelative(x = 0, y = 0)
                placeable2.placeRelative(x = w1, y = 0)
            }
        }
    }

    internal object ListKey : NavMetadataKey<Boolean>

    internal object DetailPlacehilder : NavMetadataKey<@Composable () -> Unit>

    internal object DetailKey : NavMetadataKey<Boolean>
}

/**
 * This `CompositionLocal` can be used by a detail `NavEntry` to decide whether to display a back button. Default is `true`. It is set to
 * `false` for a detail `NavEntry` when being displayed in a `ListDetailScene`.
 */
val LocalBackButtonVisibility = compositionLocalOf { true }

@Composable
fun <T : Any> rememberListDetailSceneStrategy(): ListDetailSceneStrategy<T> {
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass

    return remember(windowSizeClass) {
        ListDetailSceneStrategy(windowSizeClass)
    }
}

/**
 * A [SceneStrategy] that returns a [ListDetailScene] if:
 *
 * - the window width is over 600dp
 * - A `Detail` entry is the last item in the back stack
 * - A `List` entry is in the back stack
 *
 * Notably, when the detail entry changes the scene's key does not change. This allows the scene, rather than the NavDisplay, to handle
 * animations when the detail entry changes.
 */
class ListDetailSceneStrategy<T : Any>(val windowSizeClass: WindowSizeClass) : SceneStrategy<T> {

    @Suppress("detekt:ReturnCount")
    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {

        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)) {
            return null
        }

        if (!entries.last().metadata.contains(ListDetailScene.ListKey) && !entries.last().metadata.contains(ListDetailScene.DetailKey)) {
            return null
        }

        val detailEntry = entries.lastOrNull()?.takeIf { it.metadata.contains(ListDetailScene.DetailKey) }
        val listEntry = entries.findLast { it.metadata.contains(ListDetailScene.ListKey) } ?: return null

        // We use the list's contentKey to uniquely identify the scene.
        // This allows the detail panes to be animated in and out by the scene, rather than
        // having NavDisplay animate the whole scene out when the selected detail item changes.
        val sceneKey = listEntry.contentKey

        return ListDetailScene(
            key = sceneKey,
            previousEntries = entries.dropLast(1),
            listEntry = listEntry,
            detailEntry = detailEntry,
        )
    }

    companion object {
        /** Helper function to add metadata to a [NavEntry] indicating it can be displayed in the list pane of a [ListDetailScene]. */
        fun listPane(detailPlaceholder: @Composable () -> Unit) = metadata {
            put(ListDetailScene.ListKey, true)
            put(ListDetailScene.DetailPlacehilder, detailPlaceholder)
        }

        /** Helper function to add metadata to a [NavEntry] indicating it can be displayed in the detail pane of a the [ListDetailScene]. */
        fun detailPane() = metadata {
            put(ListDetailScene.DetailKey, true)
        }
    }
}
