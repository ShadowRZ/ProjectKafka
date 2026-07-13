package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntryDecorator

@Composable
fun <T : Any> rememberNavigatorNavEntryDecorator(navigator: Navigator): NavEntryDecorator<T> =
    remember(navigator) {
        NavigatorNavEntryDecorator(navigator)
    }

private class NavigatorNavEntryDecorator<T : Any>(navigator: Navigator) :
    NavEntryDecorator<T>(
        decorate = { entry ->
            CompositionLocalProvider(LocalNavigator provides navigator) {
                entry.Content()
            }
        }
    )
