package io.github.shadowrz.projectkafka.libraries.architecture

interface Parameters : Plugin

inline fun <reified P : Parameters> Component.paramters() =
    requireNotNull(plugins<P>().firstOrNull()) {
        "Please provide parameters of type ${P::class.qualifiedName} to component ${this::class.qualifiedName}."
    }
