@file:Suppress("UnstableApiUsage")

rootProject.name = "ProjectKafka_plugins"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
