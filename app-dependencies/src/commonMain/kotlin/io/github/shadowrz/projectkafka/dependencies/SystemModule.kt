package io.github.shadowrz.projectkafka.dependencies

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroApp
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.coroutines.supervisorScope
import io.github.shadowrz.hanekokoro.framework.runtime.renderer.Renderer
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.IOScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

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
        scope.supervisorScope(
            context = dispatchers.main + CoroutineName("ProjectKafka.SystemScope: ${system.name} (${system.id})"),
        )

    @IOScope.SystemScoped
    @SingleIn(SystemScope::class)
    @Provides
    fun providesSystemIOCoroutineScope(
        dispatchers: CoroutineDispatchers,
        @ForScope(SystemScope::class) scope: CoroutineScope,
        system: System,
    ): CoroutineScope =
        scope.supervisorScope(
            context = dispatchers.main + CoroutineName("ProjectKafka.SystemScope.IOScope: ${system.name} (${system.id})"),
        )

    @SingleIn(SystemScope::class)
    @ForScope(SystemScope::class)
    @Provides
    fun providesHanekokoroApp(
        componentFactories: Map<KClass<out Component>, Component.Factory<*>>,
        renderers: Map<KClass<out Component>, Renderer<*>>,
    ): HanekokoroApp =
        HanekokoroApp
            .Builder()
            .addComponentFactories(componentFactories)
            .addRenderers(renderers)
            .build()
}
