package io.github.shadowrz.projectkafka.navigation

import com.arkivanov.decompose.router.stack.StackNavigator

/**
 * Replaces all configurations currently in the stack with the provided [configuration],
 * but only if the provided [configuration] wasn't in the stack.
 *
 * @param onComplete called when the navigation is finished (either synchronously or asynchronously).
 */
inline fun <C : Any> StackNavigator<C>.maybeReplaceAll(
    configuration: C,
    crossinline onComplete: () -> Unit = {},
) {
    navigate(
        transformer = { it.takeIf { it.contains(configuration) } ?: listOf(configuration) },
        onComplete = { _, _ -> onComplete() },
    )
}
