package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import io.github.shadowrz.projectkafka.compose.KafkaApp
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import okio.Path

@DependencyGraph(AppScope::class)
interface AppGraph {
    @CacheDirectory val cacheDir: Path

    @FilesDirectory val filesDir: Path

    val kafkaApp: KafkaApp
}
