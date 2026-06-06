package io.github.shadowrz.projectkafka.gradle.plugins.configure

import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

internal fun Project.configureKotlin() {
    plugins.withType(JavaBasePlugin::class.java).configureEach {
        extensions.configure(JavaPluginExtension::class.java) { java ->
            java.sourceCompatibility = BuildMeta.javaVersion
            java.targetCompatibility = BuildMeta.javaVersion
        }
    }

    tasks.withType(KotlinCompilationTask::class.java).configureEach { task ->
        task.compilerOptions {
            progressiveMode.set(true)
            allWarningsAsErrors.set(true)
            freeCompilerArgs.addAll(
                "-Xannotation-default-target=param-property",
                "-Xconsistent-data-class-copy-visibility",
                "-Xexpect-actual-classes",
                // Should not need this after Kotlin 2.4.0
                "-Xcontext-parameters",
            )

            if (this is KotlinJvmCompilerOptions) {
                jvmTarget.set(BuildMeta.jvmTarget)
            }
        }
    }
}
