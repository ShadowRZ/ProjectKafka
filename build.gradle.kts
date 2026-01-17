// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.projectkafka)
    alias(libs.plugins.compose.hotreload) apply false
}

tasks.withType<AbstractTestTask>().configureEach {
    failOnNoDiscoveredTests = false
}

tasks.register("codestyle") {
    group = "CI"
    description = "Check project for codestyle problems."

    project.subprojects {
        tasks.findByName("ktlintCheck")?.let { dependsOn(it) }
        tasks.findByName("detekt")?.let { dependsOn(it) }
        tasks.findByName("lint")?.let { dependsOn(it) }
    }

    gradle.startParameter.isContinueOnFailure = true
}

kover {
    reports {
        filters {
            excludes {
                androidGeneratedClasses()
                classes(
                    "*ComposableSingletons$*",
                    // Metro
                    $$"*$MetroContributionTo*",
                    $$"*$MetroFactory*",
                    // State Providers
                    "*StateProvider",
                    "*StateProviderKt",
                    // We are currently not in position of testing Components.
                    "*Component",
                    "*Component$*",
                )
                annotatedBy(
                    // Origin annotations
                    "dev.zacsweers.metro.Origin",
                    // Preview
                    "androidx.compose.ui.tooling.preview.Preview",
                )
                packages(
                    "io.github.shadowrz.projectkafka.libraries.data.impl.db",
                    "io.github.shadowrz.projectkafka.libraries.data.impl.db.impl",
                )
            }
        }
    }
}
