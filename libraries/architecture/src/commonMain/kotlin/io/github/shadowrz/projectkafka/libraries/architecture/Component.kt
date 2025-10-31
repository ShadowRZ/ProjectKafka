package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.decompose.ComponentContext

abstract class Component(
    val componentContext: ComponentContext,
    override val plugins: List<Plugin>,
) : ComponentContext by componentContext,
    GenericComponent<ComponentContext> {
    interface Factory<C : Component> {
        fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): C
    }
}
