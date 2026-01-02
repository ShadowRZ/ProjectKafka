package io.github.shadowrz.projectkafka.dependencies

import dev.zacsweers.metro.AppScope
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
import io.github.shadowrz.projectkafka.libraries.di.annotations.IOScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus
import okio.FileSystem
import okio.SYSTEM
import kotlin.reflect.KClass

@BindingContainer
@ContributesTo(AppScope::class)
object AppModule {
    @SingleIn(AppScope::class)
    @Provides
    fun providesCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers.Default

    @SingleIn(AppScope::class)
    @Provides
    fun providesAppCoroutineScope(): CoroutineScope = MainScope() + CoroutineName("ProjectKafka")

    @IOScope
    @SingleIn(AppScope::class)
    @Provides
    fun providesAppIOCoroutineScope(
        dispatchers: CoroutineDispatchers,
        appCoroutineScope: CoroutineScope,
    ): CoroutineScope =
        appCoroutineScope.supervisorScope(
            context = dispatchers.io + CoroutineName("ProjectKafka.IOScope"),
        )

    @SingleIn(AppScope::class)
    @Provides
    fun providesFileSystem(): FileSystem = FileSystem.SYSTEM

    @SingleIn(AppScope::class)
    @ForScope(AppScope::class)
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
