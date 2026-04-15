package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.shadowrz.projectkafka.designsystem.internal.snackbar.SnackbarContent
import io.github.shadowrz.projectkafka.designsystem.internal.snackbar.SnackbarData
import io.github.shadowrz.projectkafka.designsystem.internal.snackbar.SnackbarManager
import kotlinx.coroutines.CancellationException
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.concurrent.atomics.ExperimentalAtomicApi

object Snackbar {
    internal val manager = SnackbarManager()

    /**
     * Shows or queues to be shown a [Snackbar] at the bottom.
     *
     * [SnackbarHostState] guarantees to show at most one snackbar at a time.
     *
     * To change the Snackbar appearance, change it in 'snackbarHost' on the [Scaffold].
     *
     * @param message text to be shown in the Snackbar
     * @param actionLabel optional action label to show as button in the Snackbar
     * @param withDismissAction a boolean to show a dismiss action in the Snackbar. This is
     *   recommended to be set to true for better accessibility when a Snackbar is set with a
     *   [SnackbarDuration.Indefinite]
     * @param duration duration to control how long snackbar will be shown in [SnackbarHost], either
     *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite].
     */
    @OptIn(ExperimentalAtomicApi::class)
    internal fun show(
        message: SnackbarContent,
        actionLabel: SnackbarContent? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    ) {
        manager.queue(
            SnackbarData(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                duration = duration.asMaterialDuration(),
                shown = kotlin.concurrent.atomics.AtomicBoolean(false),
            ),
        )
    }

    /**
     * Clear all pending [Snackbar] contents.
     */
    fun clear() = manager.clear()
}

@OptIn(ExperimentalAtomicApi::class)
@Composable
fun SnackbarHost(modifier: Modifier = Modifier) {
    val hostState = remember { SnackbarHostState() }

    val currentMessage by Snackbar.manager.current.collectAsStateWithLifecycle(null)

    val currentMessageContent = currentMessage?.message?.asString()
    val currentActionLabel = currentMessage?.actionLabel?.asString()

    LaunchedEffect(currentMessage) {
        currentMessage?.let {
            if (it.shown.compareAndSet(expectedValue = false, newValue = true)) {
                try {
                    hostState.showSnackbar(
                        message = currentMessageContent!!,
                        actionLabel = currentActionLabel,
                        withDismissAction = it.withDismissAction,
                        duration = it.duration,
                    )
                    Snackbar.manager.pop()
                } catch (e: CancellationException) {
                    Snackbar.manager.pop()
                    throw e
                }
            }
        }
    }

    androidx.compose.material3.SnackbarHost(
        modifier = modifier,
        hostState = hostState,
    )
}

/** Possible durations of the [Snackbar] in [SnackbarHost] */
enum class SnackbarDuration {
    /** Show the Snackbar for a short period of time */
    Short,

    /** Show the Snackbar for a long period of time */
    Long,

    /** Show the Snackbar indefinitely until explicitly dismissed or action is clicked */
    Indefinite,
}

/**
 * Shows or queues to be shown a [Snackbar] at the bottom.
 *
 * [SnackbarHostState] guarantees to show at most one snackbar at a time.
 *
 * To change the Snackbar appearance, change it in 'snackbarHost' on the [Scaffold].
 *
 * @param message text to be shown in the Snackbar
 * @param actionLabel optional action label to show as button in the Snackbar
 * @param withDismissAction a boolean to show a dismiss action in the Snackbar. This is
 *   recommended to be set to true for better accessibility when a Snackbar is set with a
 *   [SnackbarDuration.Indefinite]
 * @param duration duration to control how long snackbar will be shown in [SnackbarHost], either
 *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite].
 */
fun Snackbar.show(
    message: String,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
    duration: SnackbarDuration =
        if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
) = show(
    message = object : SnackbarContent {
        @Composable
        override fun asString(): String = message
    },
    actionLabel = actionLabel?.let {
        object : SnackbarContent {
            @Composable
            override fun asString(): String = it
        }
    },
    withDismissAction = withDismissAction,
    duration = duration,
)

/**
 * Shows or queues to be shown a [Snackbar] at the bottom.
 *
 * [SnackbarHostState] guarantees to show at most one snackbar at a time.
 *
 * To change the Snackbar appearance, change it in 'snackbarHost' on the [Scaffold].
 *
 * @param message text to be shown in the Snackbar
 * @param actionLabel optional action label to show as button in the Snackbar
 * @param withDismissAction a boolean to show a dismiss action in the Snackbar. This is
 *   recommended to be set to true for better accessibility when a Snackbar is set with a
 *   [SnackbarDuration.Indefinite]
 * @param duration duration to control how long snackbar will be shown in [SnackbarHost], either
 *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite].
 */
fun Snackbar.show(
    message: StringResource,
    actionLabel: StringResource? = null,
    withDismissAction: Boolean = false,
    duration: SnackbarDuration =
        if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
) = show(
    message = object : SnackbarContent {
        @Composable
        override fun asString(): String = stringResource(message)
    },
    actionLabel = actionLabel?.let {
        object : SnackbarContent {
            @Composable
            override fun asString(): String = stringResource(it)
        }
    },
    withDismissAction = withDismissAction,
    duration = duration,
)

private fun SnackbarDuration.asMaterialDuration(): androidx.compose.material3.SnackbarDuration =
    when (this) {
        SnackbarDuration.Short -> androidx.compose.material3.SnackbarDuration.Short
        SnackbarDuration.Long -> androidx.compose.material3.SnackbarDuration.Long
        SnackbarDuration.Indefinite -> androidx.compose.material3.SnackbarDuration.Indefinite
    }
