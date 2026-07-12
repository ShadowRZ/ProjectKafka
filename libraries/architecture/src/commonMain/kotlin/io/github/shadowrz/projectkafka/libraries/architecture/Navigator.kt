package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration

@Composable
fun rememberNavigator(
    configuration: SavedStateConfiguration,
    vararg elements: NavKey,
): Navigator {
    val backStack =
        rememberNavBackStack(
            configuration = configuration,
            elements = elements,
        )

    return remember(backStack) { NavigatorImpl(backStack) }
}

interface Navigator {
    val backStack: NavBackStack<NavKey>

    fun pop() {
        this.backStack.removeLastOrNull()
    }

    fun navigateTo(key: NavKey) {
        this.backStack.add(key)
    }
}

private class NavigatorImpl(override val backStack: NavBackStack<NavKey>) : Navigator
