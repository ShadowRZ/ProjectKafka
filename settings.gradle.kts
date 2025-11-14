@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ProjectKafka"

// Plugins
includeBuild("plugins")
// App
include(":app")
include(":appnav")
// Assets
include(":assets")

// App components
fun includeProjects(
    base: File,
    path: String,
    maxDepth: Int = 1,
) {
    base.listFiles().orEmpty().sorted().forEach { file ->
        if (file.isDirectory) {
            val newPath = "$path:${file.name}"
            val buildScriptFile = File(file, "build.gradle.kts")
            if (buildScriptFile.exists()) {
                include(newPath)
            } else if (maxDepth > 0) {
                includeProjects(file, newPath, maxDepth - 1)
            }
        }
    }
}

includeProjects(File(rootDir, "libraries"), ":libraries")
includeProjects(File(rootDir, "features"), ":features")
includeProjects(File(rootDir, "tests"), ":tests")
