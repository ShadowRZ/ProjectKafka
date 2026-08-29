package io.github.shadowrz.projectkafka.gradle.plugins

import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.ProviderFactory

@Suppress("UnstableApiUsage")
internal abstract class KafkaProperties
@Inject
constructor(
    providers: ProviderFactory,
    project: Project,
) {
    // Android: Compile, Minimum, Target SDK.
    internal val compileSdk = providers.gradleProperty(ANDROID_COMPILE_SDK).map { it.toInt() }
    internal val minSdk = providers.gradleProperty(ANDROID_MIN_SDK).map { it.toInt() }
    internal val targetSdk = providers.gradleProperty(ANDROID_TARGET_SDK).map { it.toInt() }

    // Codestyle
    internal val detektBaseline = project.isolated.rootProject.projectDirectory.file(providers.gradleProperty(CODESTYLE_DETEKT_BASELINE))
    internal val detektConfig = project.isolated.rootProject.projectDirectory.file(providers.gradleProperty(CODESTYLE_DETEKT_CONFIG))
    internal val lintBaseline = project.isolated.rootProject.projectDirectory.file(providers.gradleProperty(ANDROID_LINT_BASELINE))
    internal val lintConfig = project.isolated.rootProject.projectDirectory.file(providers.gradleProperty(ANDROID_LINT_CONFIG))

    // Compose
    internal val composeStabilityFile = project.isolated.rootProject.projectDirectory.file(providers.gradleProperty(COMPOSE_STABILITY_FILE))
    internal val composeMetrics = providers.gradleProperty(COMPOSE_METRICS).map { it.toBoolean() }.orElse(false)

    // Java
    internal val jvmTarget = providers.gradleProperty(JVM_TARGET).map { it.toInt() }

    internal val koverExcluded =
        providers
            .fileContents(project.isolated.rootProject.projectDirectory.file(providers.gradleProperty(KOVER_EXCLUDE_FILE)))
            .asText
            .map {
                it.split("\n").toSet()
            }

    companion object {
        // Android: Compile, Minimum, Target SDK.
        /**
         * Specifies the compile SDK target for all Android targets.
         *
         * Property value will be parsed with [String.toInt].
         */
        const val ANDROID_COMPILE_SDK: String = "projectkafka.android.compile-sdk"
        /**
         * Specifies the minimum SDK target for all Android targets.
         *
         * Property value will be parsed with [String.toInt].
         */
        const val ANDROID_MIN_SDK: String = "projectkafka.android.min-sdk"
        /**
         * Specifies the target SDK target for all Android targets.
         *
         * Property value will be parsed with [String.toInt].
         */
        const val ANDROID_TARGET_SDK: String = "projectkafka.android.target-sdk"
        // Codestyle
        /**
         * Path to the Androd Lint baseline file.
         *
         * Property value will be resolved to a [RegularFile][org.gradle.api.file.RegularFile], relative to the root of
         * [ProjectLayout.getProjectDirectory()][org.gradle.api.file.ProjectLayout.getProjectDirectory], using
         * [Directory.file()][org.gradle.api.file.Directory.file].
         */
        const val ANDROID_LINT_BASELINE: String = "projectkafka.codestyle.lint.baseline"
        /**
         * Path to the Android Lint config file.
         *
         * Property value will be resolved to a [RegularFile][org.gradle.api.file.RegularFile], relative to the root of
         * [ProjectLayout.getProjectDirectory()][org.gradle.api.file.ProjectLayout.getProjectDirectory], using
         * [Directory.file()][org.gradle.api.file.Directory.file].
         */
        const val ANDROID_LINT_CONFIG: String = "projectkafka.codestyle.lint.config"
        /**
         * Path to the detekt baseline file.
         *
         * Property value will be resolved to a [RegularFile][org.gradle.api.file.RegularFile], relative to the root of
         * [ProjectLayout.getProjectDirectory()][org.gradle.api.file.ProjectLayout.getProjectDirectory], using
         * [Directory.file()][org.gradle.api.file.Directory.file].
         */
        const val CODESTYLE_DETEKT_BASELINE: String = "projectkafka.codestyle.detekt.baseline"
        /**
         * Path to the detekt YAML config file.
         *
         * Property value will be resolved to a [RegularFile][org.gradle.api.file.RegularFile], relative to the root of
         * [ProjectLayout.getProjectDirectory()][org.gradle.api.file.ProjectLayout.getProjectDirectory], using
         * [Directory.file()][org.gradle.api.file.Directory.file].
         */
        const val CODESTYLE_DETEKT_CONFIG: String = "projectkafka.codestyle.detekt.config"
        // Compose
        /**
         * Path to the Compose Stability config file.
         *
         * Property value will be resolved to a [RegularFile][org.gradle.api.file.RegularFile], relative to the root of
         * [ProjectLayout.getProjectDirectory()][org.gradle.api.file.ProjectLayout.getProjectDirectory], using
         * [Directory.file()][org.gradle.api.file.Directory.file].
         */
        const val COMPOSE_STABILITY_FILE: String = "projectkafka.compose.stability-file"
        /**
         * Whether to enable Compose Compiler metrics for debugging.
         *
         * **This should not be enabled by default!**
         *
         * Property value will be parsed with [String.toBoolean].
         */
        const val COMPOSE_METRICS: String = "projectkafka.compose.metrics.enabled"
        // Java
        const val JVM_TARGET: String = "projectkafka.jvm.target"
        // Kover
        /**
         * Path to the file containing contents of project paths excluded from Kover.
         *
         * Property value will be resolved to a [RegularFile][org.gradle.api.file.RegularFile], relative to the root of
         * [ProjectLayout.getProjectDirectory()][org.gradle.api.file.ProjectLayout.getProjectDirectory], using
         * [Directory.file()][org.gradle.api.file.Directory.file].
         */
        const val KOVER_EXCLUDE_FILE: String = "projectkafka.kover.exclusions-file"
    }
}
