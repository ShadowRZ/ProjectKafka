package io.github.shadowrz.projectkafka.compose.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Multibinds
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@ContributesTo(AppScope::class)
@ContributesTo(SystemScope::class)
interface HanekokoroGraph {
    @Multibinds(allowEmpty = false) val entryProviders: Set<NavEntryProvider>
}
