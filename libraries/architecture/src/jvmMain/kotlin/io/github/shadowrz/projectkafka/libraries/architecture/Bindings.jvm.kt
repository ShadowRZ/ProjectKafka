package io.github.shadowrz.projectkafka.libraries.architecture

actual inline fun <reified T : Any> Component.bindings(): T = bindings(T::class.java)

fun <T : Any> Component.bindings(klass: Class<T>) =
    generateSequence(this) { it.parent }
        .filterIsInstance<DependencyGraphOwner>()
        .map { it.graph }
        .flatMap { it as? Collection<*> ?: listOf(it) }
        .filterIsInstance(klass)
        .firstOrNull()
        ?: error("No bindings was found for ${klass.name}")
