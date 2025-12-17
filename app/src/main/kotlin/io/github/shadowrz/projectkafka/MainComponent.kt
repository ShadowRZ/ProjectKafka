package io.github.shadowrz.projectkafka

import android.content.Intent
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroApp
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.navigation.RootFlowComponent

class MainComponent(
    hanekokoroApp: HanekokoroApp,
    context: HanekokoroContext,
    plugins: List<Plugin> = emptyList(),
) : Component(
        context = context,
        plugins = plugins,
    ) {
    fun interface OnInitCallback : Plugin {
        fun onInit(component: MainComponent)
    }

    private val onInitCallback = plugin<OnInitCallback>()

    private val readyCallback = ReadyCallback { shouldShowSplashScreen = false }

    val rootFlowComponent = childComponent<RootFlowComponent>(
        context = childContext("RootFlow"),
        plugins = listOf(readyCallback),
        hanekokoroApp = hanekokoroApp,
    )

    @Suppress("unused")
    private val retainedOnInitCallback =
        instanceKeeper.getOrCreateSimple {
            onInitCallback.onInit(this)
        }

    fun handleIntent(intent: Intent) {
        val resolvedIntent = IntentResolver.resolve(intent) ?: return
        rootFlowComponent.handleIntent(resolvedIntent)
    }

    private var shouldShowSplashScreen = true

    fun shouldShowSplashScreen() = shouldShowSplashScreen
}
