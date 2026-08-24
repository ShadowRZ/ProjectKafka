@file:Suppress("UnstableApiUsage")

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.DetektCreateBaselineTask

plugins {
    alias(libs.plugins.projectkafka)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.multiplatform) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.hotreload) apply false
    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotest) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.metro) apply false
    alias(libs.plugins.stability.analyzer) apply false
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
                    // We are currently not in position of testing NavEntryProviders.
                    "*NavEntryProvider",
                )
                annotatedBy(
                    // Origin annotations
                    "dev.zacsweers.metro.Origin",
                    // Preview
                    "androidx.compose.ui.tooling.preview.Preview",
                    "io.github.shadowrz.projectkafka.designsystem.preview.PreviewKafka",
                )
                packages(
                    "io.github.shadowrz.projectkafka.compose.di",
                    "io.github.shadowrz.projectkafka.designsystem.icons",
                    "io.github.shadowrz.projectkafka.designsystem.preview",
                    "io.github.shadowrz.projectkafka.libraries.data.impl.db",
                    "io.github.shadowrz.projectkafka.libraries.data.impl.db.impl",
                    // Trivial enough
                    "io.github.shadowrz.projectkafka.libraries.uniqueid",
                    "projectkafka.*.generated.resources",
                )
                inheritedFrom(
                    // Screens are only data classes which attachs no test value
                    "androidx.navigation3.runtime.NavKey",
                    // For Previews only
                    "androidx.compose.ui.tooling.preview.PreviewParameterProvider",
                )
            }
        }
    }
}

// Detekt
tasks.register<Detekt>("detektAll") {
    description = "Run detekt on all sources without type checking."

    buildUponDefaultConfig = true
    ignoreFailures = true
    parallel = true

    basePath = rootDir.absolutePath
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline.xml"))

    setSource(files(rootDir))

    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

tasks.register<DetektCreateBaselineTask>("detektProjectBaseline") {
    description = "Overrides current baseline."
    buildUponDefaultConfig = true
    ignoreFailures = true
    parallel = true

    basePath = rootDir.absolutePath
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline.xml"))

    setSource(files(rootDir))

    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

val otrcli = configurations.dependencyScope("otrcli").get()
val otrcliClasspath =
    configurations.resolvable("otrcliClasspath") {
        extendsFrom(otrcli)
    }

dependencies {
    otrcli(variantOf(libs.otrcli) { classifier("standalone") })
}

tasks.register<JavaExec>("testHtmlReport") {
    description = "Merge all Open Test Report test results into a unified HTML report."
    val fileTree = project.fileTree(project.projectDir)
    fileTree.include("**/open-test-report.xml")

    val htmlReportFile = project.layout.buildDirectory.file("reports/tests/open-test-report.html")

    mainClass = "org.opentest4j.reporting.cli.ReportingCli"
    args("html-report")
    classpath(otrcliClasspath)
    outputs.file(htmlReportFile)
    inputs.files(fileTree).withPathSensitivity(PathSensitivity.RELATIVE).skipWhenEmpty()
    workingDir = project.projectDir
    argumentProviders += CommandLineArgumentProvider {
        listOf(
            "--output",
            htmlReportFile.get().asFile.absolutePath,
        ) + fileTree.map { it.absolutePath }
    }
    outputs.cacheIf { false }
}

dependencies {
    detektPlugins(libs.detekt.compose)
}
