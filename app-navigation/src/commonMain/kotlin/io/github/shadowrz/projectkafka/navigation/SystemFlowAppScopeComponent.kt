package io.github.shadowrz.projectkafka.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.architecture.Parameters
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.createComponent
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner
import io.github.shadowrz.projectkafka.navigation.di.SystemGraphCache
import io.github.shadowrz.projectkafka.navigation.system.SystemFlowComponent

@AssistedInject
class SystemFlowAppScopeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    systemGraphCache: SystemGraphCache,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    DependencyGraphOwner {
    data class Params(
        val system: System,
    ) : Parameters

    private val params = paramters<Params>()
    private val readyCallback = plugin<ReadyCallback>()

    override val graph = instanceKeeper.getOrCreateSimple { systemGraphCache.getOrCreate(params.system) }

    private val componentFactories = (graph as Factories)
    internal val uiFactories = (graph as ComponentUI.Factories)

    internal val component =
        componentFactories.createComponent<SystemFlowComponent>(
            context = childContext("SystemFlowComponent.${params.system.id}"),
            parent = this,
            plugins = listOf(readyCallback),
        )

    @ContributesIntoMap(
        AppScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(SystemFlowAppScopeComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<SystemFlowAppScopeComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): SystemFlowAppScopeComponent
    }
}
