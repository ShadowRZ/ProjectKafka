package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Provides
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUIFactories
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponentFactories
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@GraphExtension(SystemScope::class)
interface SystemGraph :
    ComponentUIFactories,
    GenericComponentFactories {
    @ContributesTo(AppScope::class)
    @GraphExtension.Factory
    fun interface Factory {
        fun create(
            @Provides system: System,
        ): SystemGraph
    }
}
