package io.github.shadowrz.projectkafka

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.slack.circuit.sharedelements.SharedElementTransitionLayout
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.createGraph
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroContent
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroRoot
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
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            title = "Project Kafka",
        ) {
            HanekokoroRoot(
                hanekokoroApp = graph.hanekokoroApp,
                context = context,
                backDispatcher = backDispatcher,
            ) { context ->
                MainComponent(
                    hanekokoroApp = graph.hanekokoroApp,
                    context = context,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun MainUI(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    SharedElementTransitionLayout {
        HanekokoroContent(
            component = component.rootFlowComponent,
            modifier = modifier,
        )
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
