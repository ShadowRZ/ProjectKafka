package io.github.shadowrz.projectkafka.annotations

import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ContributesComponent(
    val scope: KClass<*>,
)
