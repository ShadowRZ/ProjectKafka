package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.decompose.ComponentContext

inline fun <reified C : Component> Component.createComponent(
    context: ComponentContext,
    plugins: List<Plugin> = emptyList(),
): C {
    val bindings: Component.Factories = bindings()
    return bindings.createComponent(context, this, plugins)
}

inline fun <reified C : Component> Component.Factories.createComponent(
    context: ComponentContext,
    parent: Component?,
    plugins: List<Plugin> = emptyList(),
): C {
    val kclass = C::class
    val factories = componentFactories()

    val factory =
        requireNotNull(factories[kclass]) {
            if (kclass == Component::class) {
                "Please provide a proper type in your createComponent calls!"
            } else {
                "No Factory for ${kclass.qualifiedName}, please check if it's injected properly."
            }
        }

    @Suppress("UNCHECKED_CAST")
    val assistedFactory = factory as? Component.Factory<C>
    val component =
        assistedFactory?.create(
            context = context,
            parent = parent,
            plugins = plugins,
        )

    return component as C
}
