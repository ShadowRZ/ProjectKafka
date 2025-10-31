package io.github.shadowrz.projectkafka.gradle.plugins

import com.google.devtools.ksp.gradle.KspAATask
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.compileOnly
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.ksp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

class MetroModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "dev.zacsweers.metro")
            apply(plugin = "com.google.devtools.ksp")

            dependencies {
                pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
                    add("kspCommonMainMetadata", project(":codegen"))
                }

                pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
                    add("kspAndroid", project(":codegen"))
                }

                pluginManager.withPlugin("com.android.application") {
                    ksp(project(":codegen"))
                    compileOnly(project(":annotations"))
                }

                pluginManager.withPlugin("com.android.library") {
                    ksp(project(":codegen"))
                    compileOnly(project(":annotations"))
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
                extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        commonMain.dependencies {
                            compileOnly(project(":annotations"))
                        }
                    }
                }
            }
        }
    }
}
