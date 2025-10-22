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
import io.github.shadowrz.projectkafka.libraries.di.annotations.IOScope
import kotlinx.coroutines.CoroutineScope

@BindingContainer
@ContributesTo(SystemScope::class)
object SystemModule {
    @ForScope(SystemScope::class)
    @SingleIn(SystemScope::class)
    @Provides
    fun providesSystemCoroutineScope(
        scope: CoroutineScope,
        dispatchers: CoroutineDispatchers,
        system: System,
    ): CoroutineScope =
        scope.childScope(
            dispatcher = dispatchers.main,
            name = "ProjectKafka.SystemScope: ${system.name} (${system.id})",
        )

    @IOScope.SystemScoped
    @SingleIn(SystemScope::class)
    @Provides
    fun providesSystemIOCoroutineScope(
        dispatchers: CoroutineDispatchers,
        @ForScope(SystemScope::class) scope: CoroutineScope,
        system: System,
    ): CoroutineScope =
        scope.childScope(
            dispatcher = dispatchers.io,
            name = "ProjectKafka.SystemScope.IOScope: ${system.name} (${system.id})",
        )
}
