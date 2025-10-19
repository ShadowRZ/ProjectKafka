package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.decompose.ComponentContext

fun interface Resolver<NavTarget, Resolved> {
    fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved
}
