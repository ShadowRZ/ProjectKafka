package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.extensions.compileOnly
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.ksp
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MetroModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "dev.zacsweers.metro")
            apply(plugin = "com.google.devtools.ksp")

            dependencies {
                pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
                    add("kspCommonMainMetadata", libs.hanekokoro.framework.codegen)
                }

                pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
                    add("kspAndroid", libs.hanekokoro.framework.codegen)
                }

                pluginManager.withPlugin("com.android.application") {
                    ksp(libs.hanekokoro.framework.codegen)
                    compileOnly(libs.hanekokoro.framework.annotations)
                }

                pluginManager.withPlugin("com.android.library") {
                    ksp(libs.hanekokoro.framework.codegen)
                    compileOnly(libs.hanekokoro.framework.annotations)
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
                extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        commonMain.dependencies {
                            compileOnly(libs.hanekokoro.framework.annotations)
                        }
                    }
                }
            }
        }
    }
}
