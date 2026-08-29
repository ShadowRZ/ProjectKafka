package io.github.shadowrz.projectkafka.gradle.plugins.configure

import com.android.build.api.dsl.Lint
import io.github.shadowrz.projectkafka.gradle.plugins.KafkaProperties
import org.gradle.api.Project

internal fun Project.configureLint(
    lint: Lint,
    kafkaProperties: KafkaProperties,
    application: Boolean = false,
) {
    lint.apply {
        // Format text output for convenience.
        explainIssues = true
        noLines = false
        quiet = true

        // Turn off the "lintVital<buildVariant>" tasks that are included by default in release
        // builds. We run lint separately from release builds on CI, which makes these tasks
        // redundant.
        checkReleaseBuilds = false

        // We run lint on each library, so we don't want transitive checking of each dependency
        // However for Application, do check dependencies.
        checkDependencies = application

        // Disable dependency checks that suggest to change them. We want libraries to be
        // intentional with their dependency version bumps.
        disable += "KtxExtensionAvailable"
        disable += "GradleDependency"
        disable += "AndroidGradlePluginVersion"
        disable += "NewerVersionAvailable"

        fatal += "Assert"
        fatal += "NewApi"
        fatal += "ObsoleteSdkInt"

        // Too many Kotlin features require synthetic accessors - we want to rely on R8 to
        // remove these accessors
        disable += "SyntheticAccessor"

        baseline = kafkaProperties.lintBaseline.orNull?.asFile
        lintConfig = kafkaProperties.lintConfig.orNull?.asFile
    }
}

internal fun Project.configureNonAndroidLint(kafkaProperties: KafkaProperties) {
    extensions.configure(Lint::class.java) { lint ->
        configureLint(
            lint = lint,
            kafkaProperties = kafkaProperties,
        )
    }
}
