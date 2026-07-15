package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

interface NavEntryProvider {
    fun EntryProviderScope<NavKey>.provideEntry(
        navigator: Navigator,
        sharedTransitionScope: SharedTransitionScope,
    )
}
