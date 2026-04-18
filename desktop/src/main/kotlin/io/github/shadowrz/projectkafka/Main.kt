package io.github.shadowrz.projectkafka

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.crossfade
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.zacsweers.metro.createGraph
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroRoot
import io.github.shadowrz.projectkafka.compose.MainComponent
import io.github.shadowrz.projectkafka.di.AppGraph
import javax.swing.SwingUtilities

fun main() {
    val lifecycle = LifecycleRegistry()

    val backDispatcher = BackDispatcher()
    val graph = createGraph<AppGraph>()
    val context = runOnUiThread {
        DefaultComponentContext(lifecycle = lifecycle, backHandler = backDispatcher)
    }

    application {
        setSingletonImageLoaderFactory { context ->
            ImageLoader
                .Builder(context)
                .crossfade(true)
                .memoryCache {
                    MemoryCache.Builder().maxSizePercent(context, 0.25).build()
                }.diskCache {
                    DiskCache
                        .Builder()
                        .directory(graph.cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }.build()
        }

        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            title = "Project Kafka",
        ) {
            HanekokoroRoot(
                hanekokoroApp = graph.hanekokoroApp,
                context = context,
            ) { context ->
                MainComponent(
                    hanekokoroApp = graph.hanekokoroApp,
                    context = context,
                    plugins = listOf(MainComponent.OnInitCallback.Empty),
                )
            }
        }
    }
}

@Suppress("detekt:TooGenericExceptionCaught")
internal fun <T> runOnUiThread(block: () -> T): T {
    if (SwingUtilities.isEventDispatchThread()) {
        return block()
    }

    var error: Throwable? = null
    var result: T? = null

    SwingUtilities.invokeAndWait {
        try {
            result = block()
        } catch (e: Throwable) {
            error = e
        }
    }

    error?.also { throw it }

    @Suppress("UNCHECKED_CAST")
    return result as T
}
