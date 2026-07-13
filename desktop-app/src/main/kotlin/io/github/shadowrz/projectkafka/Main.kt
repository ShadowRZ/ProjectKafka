package io.github.shadowrz.projectkafka

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.crossfade
import dev.zacsweers.metro.createGraph
import io.github.shadowrz.projectkafka.di.AppGraph
import io.github.vinceglb.filekit.FileKit

fun main() {
    val graph = createGraph<AppGraph>()
    val kafkaApp = graph.kafkaApp

    FileKit.init(
        filesDir = graph.filesDir.toFile(),
        cacheDir = graph.cacheDir.toFile(),
    )

    application {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .crossfade(true)
                .memoryCache {
                    MemoryCache.Builder().maxSizePercent(context, 0.25).build()
                }
                .diskCache {
                    DiskCache.Builder().directory(graph.cacheDir.resolve("image_cache")).maxSizePercent(0.02).build()
                }
                .build()
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Project Kafka",
        ) {
            kafkaApp.Content()
        }
    }
}
