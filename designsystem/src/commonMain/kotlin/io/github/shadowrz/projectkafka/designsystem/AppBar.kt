package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.material3.TopAppBarScrollBehavior as MaterialTopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState as MaterialTopAppBarState

/**
 * Wrapper class to hide the Material3 [TopAppBarScrollBehavior][MaterialTopAppBarScrollBehavior] implementation detail.
 *
 * @see androidx.compose.material3.TopAppBarScrollBehavior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Stable
class TopAppBarScrollBehavior internal constructor(
    internal val scrollBehavior: MaterialTopAppBarScrollBehavior,
) {
    /**
     * A [TopAppBarState] that is attached to this behavior and is read and updated when scrolling
     * happens.
     */
    val state: TopAppBarState
        get() = TopAppBarState(scrollBehavior.state)

    /**
     * Indicates whether the top app bar is pinned.
     *
     * A pinned app bar will stay fixed in place when content is scrolled and will not react to any
     * drag gestures.
     */
    val isPinned: Boolean by scrollBehavior::isPinned

    /**
     * An optional [androidx.compose.animation.core.AnimationSpec] that defines how the top app bar snaps to either fully collapsed
     * or fully extended state when a fling or a drag scrolled it into an intermediate position.
     */
    val snapAnimationSpec: AnimationSpec<Float>? by scrollBehavior::snapAnimationSpec

    /**
     * An optional [androidx.compose.animation.core.DecayAnimationSpec] that defined how to fling the top app bar when the user
     * flings the app bar itself, or the content below it.
     */
    val flingAnimationSpec: DecayAnimationSpec<Float>? by scrollBehavior::flingAnimationSpec

    /**
     * A [androidx.compose.ui.input.nestedscroll.NestedScrollConnection] that should be attached to a [Modifier.nestedScroll] in order to
     * keep track of the scroll events.
     */
    val nestedScrollConnection: NestedScrollConnection by scrollBehavior::nestedScrollConnection
}

/**
 * Wrapper class to hide the Material3 [TopAppBarState][MaterialTopAppBarState] implementation detail.
 */
@Stable
class TopAppBarState internal constructor(
    internal val state: MaterialTopAppBarState,
) {
    /**
     * The top app bar's height offset limit in pixels, which represents the limit that a top app
     * bar is allowed to collapse to.
     *
     * Use this limit to coerce the [heightOffset] value when it's updated.
     */
    var heightOffsetLimit by state::heightOffsetLimit

    /**
     * The top app bar's current height offset in pixels. This height offset is applied to the fixed
     * height of the app bar to control the displayed height when content is being scrolled.
     *
     * Updates to the [heightOffset] value are coerced between zero and [heightOffsetLimit].
     */
    var heightOffset by state::heightOffset

    /**
     * The total offset of the content scrolled under the top app bar.
     *
     * The content offset is used to compute the [overlappedFraction], which can later be read by an
     * implementation.
     *
     * This value is updated by a [TopAppBarScrollBehavior] whenever a nested scroll connection
     * consumes scroll events. A common implementation would update the value to be the sum of all
     * [NestedScrollConnection.onPostScroll] `consumed.y` values.
     */
    var contentOffset by state::contentOffset

    /**
     * A value that represents the collapsed height percentage of the app bar.
     *
     * A `0.0` represents a fully expanded bar, and `1.0` represents a fully collapsed bar (computed
     * as [heightOffset] / [heightOffsetLimit]).
     */
    val collapsedFraction by state::collapsedFraction

    /**
     * A value that represents the percentage of the app bar area that is overlapping with the
     * content scrolled behind it.
     *
     * A `0.0` indicates that the app bar does not overlap any content, while `1.0` indicates that
     * the entire visible app bar area overlaps the scrolled content.
     */
    val overlappedFraction by state::overlappedFraction
}

/**
 * Creates a [TopAppBarState] that is remembered across compositions.
 *
 * @param initialHeightOffsetLimit the initial value for [TopAppBarState.heightOffsetLimit], which
 *   represents the pixel limit that a top app bar is allowed to collapse when the scrollable
 *   content is scrolled
 * @param initialHeightOffset the initial value for [TopAppBarState.heightOffset]. The initial
 *   offset height offset should be between zero and [initialHeightOffsetLimit].
 * @param initialContentOffset the initial value for [TopAppBarState.contentOffset]
 */
@Composable
fun rememberTopAppBarState(
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f,
    initialContentOffset: Float = 0f,
): TopAppBarState {
    val state = androidx.compose.material3.rememberTopAppBarState(
        initialHeightOffsetLimit = initialHeightOffsetLimit,
        initialHeightOffset = initialHeightOffset,
        initialContentOffset = initialContentOffset,
    )

    return remember(state) { TopAppBarState(state) }
}
