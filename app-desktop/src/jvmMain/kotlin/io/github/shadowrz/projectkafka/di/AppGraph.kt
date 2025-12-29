package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus

@DependencyGraph(AppScope::class)
interface AppGraph {
    @SingleIn(AppScope::class)
    @Provides
    fun providesAppCoroutineScope(): CoroutineScope = MainScope() + CoroutineName("ProjectKafka")

    @SingleIn(AppScope::class)
    @Provides
    fun providesBuildMeta(): BuildMeta =
        BuildMeta(
            applicationName = "Project Kafka",
            applicationId = "io.github.shadowrz.projectkafka",
            versionName = "1.0",
            versionCode = 1,
        )
}
