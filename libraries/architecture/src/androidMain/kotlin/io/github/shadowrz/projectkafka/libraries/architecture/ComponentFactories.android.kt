package io.github.shadowrz.projectkafka.libraries.architecture

import android.content.Context
import com.arkivanov.decompose.ComponentContext

inline fun <reified C : Component> Context.createComponent(
    context: ComponentContext,
    parent: Component?,
    plugins: List<Plugin> = emptyList(),
): C {
    val bindings: Component.Factories = bindings()
    return bindings.createComponent(context, parent, plugins)
}
