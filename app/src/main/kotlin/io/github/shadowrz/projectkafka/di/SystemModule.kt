package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.core.coroutine.childScope
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.CoroutineScope

@BindingContainer
@ContributesTo(SystemScope::class)
object SystemModule {
    @ForScope(SystemScope::class)
    @SingleIn(SystemScope::class)
    @Provides
    fun providesSystemCoroutineScope(
        appCoroutineScope: CoroutineScope,
        dispatchers: CoroutineDispatchers,
        system: System,
    ): CoroutineScope =
        appCoroutineScope.childScope(
            dispatcher = dispatchers.main,
            name = "ProjectKafka.SystemScope: ${system.name} (${system.id})",
        )
}
