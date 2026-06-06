package io.github.shadowrz.projectkafka.gradle.plugins.configure

import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

internal fun Project.configureKotlin() {
    plugins.withType(JavaBasePlugin::class.java).configureEach {
        extensions.configure(JavaPluginExtension::class.java) {
            sourceCompatibility = BuildMeta.javaVersion
            targetCompatibility = BuildMeta.javaVersion
        }
    }

    tasks.withType(KotlinCompilationTask::class.java).configureEach {
        compilerOptions {
            progressiveMode.set(true)
            allWarningsAsErrors.set(true)
            freeCompilerArgs.addAll(
                "-Xconsistent-data-class-copy-visibility",
                "-Xexpect-actual-classes",
            )

            if (this is KotlinJvmCompilerOptions) {
                jvmTarget.set(BuildMeta.jvmTarget)
            }
        }
    }
}
