package io.github.shadowrz.projectkafka.compose.di

import dev.zacsweers.metro.Multibinds
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider

interface HanekokoroGraph {
    @Multibinds(allowEmpty = false) val entryProviders: Set<NavEntryProvider>
}
