package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Multibinds
import kotlin.reflect.KClass

abstract class Component(
    val componentContext: ComponentContext,
    override val plugins: List<Plugin>,
) : ComponentContext by componentContext,
    PluginsOwner,
    ParentOwner<Component> {
    interface Factory<C : Component> {
        fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): C
    }

    interface Factories {
        @Multibinds
        fun componentFactories(): Map<KClass<out Component>, Factory<*>>
    }
}
