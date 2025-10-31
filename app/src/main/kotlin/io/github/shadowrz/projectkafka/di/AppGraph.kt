package io.github.shadowrz.projectkafka.di

import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext

@DependencyGraph(AppScope::class)
interface AppGraph :
    ComponentUI.Factories,
    GenericComponent.Factories {
    val systemGraphFactory: SystemGraph.Factory

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides @ApplicationContext context: Context,
        ): AppGraph
    }
}
