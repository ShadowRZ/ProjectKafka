package io.github.shadowrz.projectkafka.gradle.plugins.extensions

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

internal val Project.libs
    get(): VersionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

internal val Project.isRootProject: Boolean
    get() = rootProject == this

// From https://github.com/slackhq/foundry
/**
 * Executes the given action either upon the finalization of [AndroidComponentsExtension] DSL or
 * after the project evaluation, providing compatibility for both scenarios.
 */
internal fun Project.afterEvaluateSafe(action: Action<Project>) {
    val androidComponents =
        project.extensions.findByName("androidComponents") as? AndroidComponentsExtension<*, *, *>?

    androidComponents?.finalizeDsl { action.execute(this) } ?: afterEvaluate(action)
}
