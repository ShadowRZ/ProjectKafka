package io.github.shadowrz.projectkafka.libraries.architecture

import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
)
@MapKey
annotation class ComponentKey(
    val value: KClass<out GenericComponent<*>>,
)
