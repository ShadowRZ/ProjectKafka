package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroApp
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import okio.Path

@DependencyGraph(AppScope::class)
interface AppGraph {
    @CacheDirectory val cacheDir: Path

    @ForScope(AppScope::class)
    val hanekokoroApp: HanekokoroApp
}
