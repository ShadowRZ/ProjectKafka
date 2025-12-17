package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.kotlin.multiplatform.library")
            apply(plugin = "io.github.shadowrz.projectkafka.multiplatform")
            apply(plugin = "io.github.shadowrz.projectkafka.compose")
            apply(plugin = "dev.zacsweers.metro")
            apply(plugin = "com.google.devtools.ksp")

            dependencies {
                add("kspCommonMainMetadata", libs.hanekokoro.framework.codegen)
                add("kspAndroid", libs.hanekokoro.framework.codegen)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
                    androidResources {
                        enable = true
                    }
                }

                sourceSets {
                    commonMain.dependencies {
                        implementation(libs.decompose)
                        implementation(libs.hanekokoro.framework.annotations)
                        implementation(libs.hanekokoro.framework.integration)
                        implementation(libs.hanekokoro.framework.runtime)
                    }
                }
            }
        }
    }
}
