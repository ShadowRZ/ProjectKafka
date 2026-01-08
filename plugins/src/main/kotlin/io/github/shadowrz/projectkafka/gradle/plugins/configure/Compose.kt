package io.github.shadowrz.projectkafka.gradle.plugins.configure

import com.android.build.api.dsl.CommonExtension
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.androidTestImplementation
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.implementation
import libs
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal fun CommonExtension<*, *, *, *, *, *>.configureCompose() {
    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

@Suppress("UnstableApiUsage")
internal fun Project.configureComposeCompiler() {
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }

        fun Provider<*>.relativeToRootProject(dir: String) =
            map {
                isolated.rootProject.projectDirectory
                    .dir("build")
                    .dir(projectDir.toRelativeString(rootDir))
            }.map { it.dir(dir) }

        project.providers
            .gradleProperty("enableComposeCompilerMetrics")
            .onlyIfTrue()
            .relativeToRootProject("compose/metrics")
            .let(metricsDestination::set)

        project.providers
            .gradleProperty("enableComposeCompilerReports")
            .onlyIfTrue()
            .relativeToRootProject("compose/reports")
            .let(reportsDestination::set)

        stabilityConfigurationFiles
            .add(isolated.rootProject.projectDirectory.file("config/compose/compose.conf"))
    }
}

internal fun DependencyHandlerScope.glanceLibraries(libs: LibrariesForLibs) {
    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.appwidget.preview)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.glance.preview)
}

internal fun DependencyHandlerScope.composeMultiplatformLibraries(libs: LibrariesForLibs) {
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.preview)
    implementation(libs.compose.ui)
}

internal fun KotlinDependencyHandler.composeMultiplatformLibraries(libs: LibrariesForLibs) {
    implementation(libs.compose.components.preview)
    implementation(libs.compose.components.resources)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.preview)
    implementation(libs.compose.ui)
}

internal fun KotlinDependencyHandler.glanceLibraries(libs: LibrariesForLibs) {
    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.appwidget.preview)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.glance.preview)
}

internal fun Project.addComposeDependencies() {
    pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets {
                commonMain.dependencies {
                    composeMultiplatformLibraries(libs)
                }
            }
        }
    }

    pluginManager.withPlugin("com.android.library") {
        dependencies {
            composeMultiplatformLibraries(libs)
        }
    }

    pluginManager.withPlugin("com.android.application") {
        dependencies {
            composeMultiplatformLibraries(libs)
        }
    }
}

internal fun Project.addGlanceDependencies() {
    pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
            pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
                sourceSets {
                    if (hasProperty("androidMain")) {
                        androidMain.dependencies {
                            glanceLibraries(libs)
                        }
                    }
                }
            }
            sourceSets {
                commonMain.dependencies {
                    implementation(libs.compose.runtime)
                }
            }
        }
    }

    pluginManager.withPlugin("com.android.library") {
        dependencies {
            glanceLibraries(libs)
        }
    }

    pluginManager.withPlugin("com.android.application") {
        dependencies {
            glanceLibraries(libs)
        }
    }
}
