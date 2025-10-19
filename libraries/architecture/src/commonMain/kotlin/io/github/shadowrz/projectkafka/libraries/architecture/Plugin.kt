package io.github.shadowrz.projectkafka.libraries.architecture

interface Plugin

inline fun <reified P : Plugin> Component.plugins(): List<P> = this.plugins.filterIsInstance<P>()

inline fun <reified P : Plugin> Component.plugin(): P =
    requireNotNull(maybePlugin<P>()) {
        "Please provide a plugin of type ${P::class.qualifiedName} to component ${this::class.qualifiedName}."
    }

inline fun <reified P : Plugin> Component.maybePlugin(): P? = plugins<P>().firstOrNull()
