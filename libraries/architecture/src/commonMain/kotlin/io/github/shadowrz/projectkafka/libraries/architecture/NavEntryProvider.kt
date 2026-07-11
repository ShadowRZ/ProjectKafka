package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

interface NavEntryProvider {
    fun EntryProviderScope<NavKey>.provideEntry()
}
