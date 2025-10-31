package io.github.shadowrz.projectkafka

import android.content.Context
import android.content.Intent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.createComponent
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.navigation.RootFlowComponent

internal class MainComponent(
    componentContext: ComponentContext,
    plugins: List<Plugin>,
    @ApplicationContext context: Context,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    DependencyGraphOwner {
    fun interface OnInitCallback : Plugin {
        fun onInit(component: MainComponent)
    }

    private val onInitCallback = plugin<OnInitCallback>()

    override val graph = (context as DependencyGraphOwner).graph

    private val readyCallback = ReadyCallback { shouldShowSplashScreen = false }

    val rootFlowComponent =
        (graph as GenericComponent.Factories).createComponent<ComponentContext, RootFlowComponent>(
            context = childContext("RootFlow"),
            parent = this,
            plugins = listOf(readyCallback),
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
