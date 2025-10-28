package io.github.shadowrz.projectkafka

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.crossfade
import dev.zacsweers.metro.createGraphFactory
import io.github.shadowrz.projectkafka.di.AppGraph
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner
import io.github.shadowrz.projectkafka.libraries.di.ResetDependencyGraph

class KafkaApplication :
    Application(),
    DependencyGraphOwner,
    ResetDependencyGraph,
    SingletonImageLoader.Factory {
    override var graph: AppGraph = createGraphFactory<AppGraph.Factory>().create(this)

    override fun resetGraph() {
        graph = createGraphFactory<AppGraph.Factory>().create(this)
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader
            .Builder(context)
            .crossfade(true)
            .memoryCache {
                MemoryCache.Builder().maxSizePercent(context, 0.25).build()
            }.diskCache {
                DiskCache
                    .Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }.build()
}
