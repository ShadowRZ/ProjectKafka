package io.github.shadowrz.projectkafka.navigation

import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Parameters
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner
import io.github.shadowrz.projectkafka.libraries.systemgraph.SystemGraphCache
import io.github.shadowrz.projectkafka.navigation.di.SystemBinding
import io.github.shadowrz.projectkafka.navigation.system.SystemFlowComponent

@AssistedInject
@HanekokoroInject(AppScope::class)
class SystemFlowAppScopeComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    systemGraphCache: SystemGraphCache,
) : Component(
        context = context,
        plugins = plugins,
    ),
    DependencyGraphOwner {
    data class Params(
        val system: System,
    ) : Parameters

    private val params = paramters<Params>()
    private val readyCallback = plugin<ReadyCallback>()

    override val graph = instanceKeeper.getOrCreateSimple { systemGraphCache.getOrCreate(params.system) }

    internal val hanekokoroApp = (graph as SystemBinding).hanekokoroApp

    internal val component =
        childComponent<SystemFlowComponent>(
            context = childContext("SystemFlowComponent.${params.system.id}"),
            plugins = listOf(readyCallback),
            hanekokoroApp = hanekokoroApp,
        )
}
