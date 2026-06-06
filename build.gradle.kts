// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.projectkafka)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.multiplatform) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.hotreload) apply false
    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kotest) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.metro) apply false
}

subprojects {
    apply(plugin = "com.autonomousapps.dependency-analysis")
}

tasks.register("codestyle") {
    group = "CI"
    description = "Check project for codestyle problems."

    project.subprojects {
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
                    "projectkafka.*.generated.resources",
                )
            }
        }
    }
}
