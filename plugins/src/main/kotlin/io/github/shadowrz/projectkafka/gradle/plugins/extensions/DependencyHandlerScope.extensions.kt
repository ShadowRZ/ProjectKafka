@file:Suppress("ktlint:standard:function-naming")

package io.github.shadowrz.projectkafka.gradle.plugins.extensions

import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.detektPlugins(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "detektPlugins",
        dependencyNotation,
    )

