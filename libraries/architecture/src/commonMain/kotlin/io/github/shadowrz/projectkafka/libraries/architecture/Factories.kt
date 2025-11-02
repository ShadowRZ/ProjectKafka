package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Multibinds
import io.github.shadowrz.hanekokoro.framework.runtime.ComponentUI
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import kotlin.reflect.KClass

interface GenericComponentFactories {
    @Multibinds
    fun componentFactories(): Map<KClass<*>, GenericComponent.Factory<*>>
}

interface ComponentUIFactories {
    @Multibinds
    fun uiFactories(): Map<KClass<*>, ComponentUI<*>>
}

inline fun <reified C : GenericComponent<ComponentContext>> GenericComponent<ComponentContext>.createComponent(
    context: ComponentContext,
    plugins: List<Plugin> = emptyList(),
): C {
    val bindings: GenericComponentFactories = bindings()
    return bindings.createComponent(context, this, plugins)
}

inline fun <Ctx : Any, reified C : GenericComponent<Ctx>> GenericComponentFactories.createComponent(
    context: Ctx,
    parent: GenericComponent<*>?,
    plugins: List<Plugin> = emptyList(),
): C {
    val kclass = C::class
    val factories = componentFactories()

    val factory =
        requireNotNull(factories[kclass]) {
            if (kclass == GenericComponent::class) {
                "Please provide a proper type in your createComponent calls!"
            } else {
                "No Factory for ${kclass.qualifiedName}, please check if it's injected properly."
            }
        }

    @Suppress("UNCHECKED_CAST")
    val assistedFactory = factory as? GenericComponent.Factory<Ctx>
    val component =
        assistedFactory?.create(
            context = context,
            parent = parent,
            plugins = plugins,
        )

    return component as C
}
