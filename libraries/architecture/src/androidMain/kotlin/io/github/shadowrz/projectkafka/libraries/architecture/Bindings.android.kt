package io.github.shadowrz.projectkafka.libraries.architecture

import android.content.Context
import android.content.ContextWrapper
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner

inline fun <reified T : Any> Context.bindings() = bindings(T::class.java)

fun <T : Any> Context.bindings(klass: Class<T>) =
    generateSequence(this) { (it as? ContextWrapper)?.baseContext }
        .plus(applicationContext)
        .filterIsInstance<DependencyGraphOwner>()
        .map { it.graph }
        .flatMap { it as? Collection<*> ?: listOf(it) }
        .filterIsInstance(klass)
        .firstOrNull()
        ?: error("No bindings was found for ${klass.name}")
