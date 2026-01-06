// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("io.github.shadowrz.projectkafka")
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kotest) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.metro) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
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
                    "io.github.shadowrz.projectkakfa.libraries.data.impl.db",
                    "io.github.shadowrz.projectkakfa.libraries.data.impl.db.impl",
                )
            }
        }
    }
}
