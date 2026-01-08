@file:Suppress("ktlint:standard:function-naming")

package io.github.shadowrz.projectkafka.gradle.plugins.extensions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal fun DependencyHandlerScope.api(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "api",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.compileOnly(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "compileOnly",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.implementation(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "implementation",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.testImplementation(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "testImplementation",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.androidTestImplementation(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "androidTestImplementation",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.debugImplementation(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "debugImplementation",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.ksp(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "ksp",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.detektPlugins(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "detektPlugins",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.coreLibraryDesugaring(dependencyNotation: Any): Dependency? =
    dependencies.add(
        "coreLibraryDesugaring",
        dependencyNotation,
    )

internal fun DependencyHandlerScope.commonLibraries(libs: LibrariesForLibs) {
    implementation(libs.timber)
    implementation(libs.uri)
}

fun DependencyHandlerScope.allFeaturesApi(project: Project) =
    addAll(
        project,
        prefix = ":features",
        suffix = ":api",
    )

fun KotlinDependencyHandler.allFeaturesApi(project: Project) =
    addAll(
        project,
        prefix = ":features",
        suffix = ":api",
    )

fun DependencyHandlerScope.allFeaturesImpl(project: Project) =
    addAll(
        project,
        prefix = ":features",
        suffix = ":impl",
    )

fun KotlinDependencyHandler.allFeaturesImpl(project: Project) =
    addAll(
        project,
        prefix = ":features",
        suffix = ":impl",
    )

fun DependencyHandlerScope.allLibrariesImpl(project: Project) =
    addAll(
        project,
        prefix = ":libraries",
        suffix = ":impl",
    )

fun KotlinDependencyHandler.allLibrariesImpl(project: Project) =
    addAll(
        project,
        prefix = ":libraries",
        suffix = ":impl",
    )

private fun DependencyHandlerScope.addAll(
    project: Project,
    prefix: String,
    suffix: String,
) {
    val subProjects =
        project.rootProject.subprojects.filter {
            it.path.startsWith(prefix) && it.path.endsWith(suffix)
        }

    subProjects.forEach { add("implementation", it) }
}

private fun KotlinDependencyHandler.addAll(
    project: Project,
    prefix: String,
    suffix: String,
) {
    val subProjects =
        project.rootProject.subprojects.filter {
            it.path.startsWith(prefix) && it.path.endsWith(suffix)
        }

    subProjects.forEach { implementation(it) }
}
