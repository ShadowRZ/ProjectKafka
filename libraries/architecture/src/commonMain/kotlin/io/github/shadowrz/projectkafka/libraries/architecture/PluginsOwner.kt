package io.github.shadowrz.projectkafka.libraries.architecture

interface PluginsOwner {
    val plugins: List<Plugin>
}

inline fun <reified P : Plugin> PluginsOwner.plugins(): List<P> = this.plugins.filterIsInstance<P>()

inline fun <reified P : Plugin> PluginsOwner.plugin(): P =
    requireNotNull(maybePlugin<P>()) {
        "Please provide a plugin of type ${P::class.qualifiedName} to component ${this::class.qualifiedName}."
    }

inline fun <reified P : Plugin> PluginsOwner.maybePlugin(): P? = plugins<P>().firstOrNull()
