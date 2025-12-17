package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Provides
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@GraphExtension(SystemScope::class)
interface SystemGraph {
    @ContributesTo(AppScope::class)
    @GraphExtension.Factory
    fun interface Factory {
        fun create(
            @Provides system: System,
        ): SystemGraph
    }
}
