package io.github.shadowrz.projectkafka.libraries.architecture

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin

inline fun <reified C : GenericComponent<ComponentContext>> Context.createComponent(
    context: ComponentContext,
    parent: GenericComponent<*>?,
    plugins: List<Plugin> = emptyList(),
): C {
    val bindings: GenericComponentFactories = bindings()
    return bindings.createComponent(context, parent, plugins)
}
