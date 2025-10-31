package io.github.shadowrz.projectkafka.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
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
@ContributesComponent(AppScope::class)
class SystemFlowAppScopeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
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

    private val componentFactories = (graph as GenericComponent.Factories)
    internal val uiFactories = (graph as ComponentUI.Factories)

    internal val component =
        componentFactories.createComponent<ComponentContext, SystemFlowComponent>(
            context = childContext("SystemFlowComponent.${params.system.id}"),
            parent = this,
            plugins = listOf(readyCallback),
        )
}
