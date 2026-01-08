package io.github.shadowrz.projectkafka

import com.arkivanov.decompose.childContext
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroApp
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
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
    val rootFlowComponent = childComponent<RootFlowComponent>(
        context = childContext("RootFlow"),
        plugins = listOf(ReadyCallback {}),
        hanekokoroApp = hanekokoroApp,
    )
}
