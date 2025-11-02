package io.github.shadowrz.projectkafka.libraries.architecture

import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner

actual inline fun <reified T : Any> GenericComponent<*>.bindings(): T = bindings(T::class.java)

fun <T : Any> GenericComponent<*>.bindings(klass: Class<T>) =
    generateSequence(this) { it.parent }
        .filterIsInstance<DependencyGraphOwner>()
        .map { it.graph }
        .flatMap { it as? Collection<*> ?: listOf(it) }
        .filterIsInstance(klass)
        .firstOrNull()
        ?: error("No bindings was found for ${klass.name}")
