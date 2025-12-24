@file:Suppress("UnstableApiUsage")

package io.github.shadowrz.projectkafka.gradle.plugins.configure

import org.gradle.api.Project

internal val excludedKoverProjects = listOf(
    ":app",
    ":assets",
    ":buildmeta",
    ":libraries:core",
    ":libraries:di",
    ":libraries:icons",
    ":libraries:strings",
)

internal fun Project.koverSubprojects() =
    project.rootProject.subprojects
        .filter {
            it.isolated.projectDirectory
                .file("build.gradle.kts")
                .asFile
                .exists()
        }.map { it.path }
        .sorted()
        .filter { it !in excludedKoverProjects }
