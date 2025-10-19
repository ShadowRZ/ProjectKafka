package io.github.shadowrz.projectkafka.gradle.plugins.configure

import com.android.build.api.dsl.CommonExtension
import io.github.shadowrz.projectkafka.gradle.plugins.dsl.compose
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.androidTestImplementation
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.debugImplementation
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
import org.jetbrains.compose.ComposePlugin
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

internal fun DependencyHandlerScope.composeLibraries(
    libs: LibrariesForLibs,
    composeBom: Provider<MinimalExternalModuleDependency>,
) {
    implementation(composeBom)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    androidTestImplementation(composeBom)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

internal fun DependencyHandlerScope.glanceLibraries(
    libs: LibrariesForLibs,
    composeBom: Provider<MinimalExternalModuleDependency>,
) {
    implementation(composeBom)
    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.appwidget.preview)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.glance.preview)

    androidTestImplementation(composeBom)
}

internal fun KotlinDependencyHandler.composeLibraries(
    libs: LibrariesForLibs,
    composeBom: Provider<MinimalExternalModuleDependency>,
) {
    implementation(composeBom)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
}

internal fun KotlinDependencyHandler.composeMultiplatformLibraries(compose: ComposePlugin.Dependencies) {
    implementation(compose.components.uiToolingPreview)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.preview)
    implementation(compose.ui)
}

internal fun KotlinDependencyHandler.glanceLibraries(
    libs: LibrariesForLibs,
    composeBom: Provider<MinimalExternalModuleDependency>,
) {
    implementation(composeBom)
    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.appwidget.preview)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.glance.preview)
}

internal fun Project.addComposeDependencies() {
    val composeBom = dependencies.platform(libs.androidx.compose.bom)

    pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
            pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
                sourceSets {
                    if (hasProperty("androidDeviceTest")) {
                        getByName("androidDeviceTest") {
                            dependencies {
                                implementation(composeBom)
                            }
                        }
                    }
                }
            }
            sourceSets {
                commonMain.dependencies {
                    composeLibraries(libs, composeBom)
                    composeMultiplatformLibraries(compose)
                }
            }
        }
    }

    pluginManager.withPlugin("com.android.library") {
        dependencies {
            composeLibraries(libs, composeBom)
        }
    }

    pluginManager.withPlugin("com.android.application") {
        dependencies {
            composeLibraries(libs, composeBom)
        }
    }
}

internal fun Project.addGlanceDependencies() {
    val composeBom = dependencies.platform(libs.androidx.compose.bom)

    pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
            pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
                sourceSets {
                    if (hasProperty("androidDeviceTest")) {
                        getByName("androidDeviceTest") {
                            dependencies {
                                implementation(composeBom)
                            }
                        }
                    }
                    if (hasProperty("androidMain")) {
                        androidMain.dependencies {
                            glanceLibraries(libs, composeBom)
                        }
                    }
                }
            }
            sourceSets {
                commonMain.dependencies {
                    implementation(libs.androidx.compose.runtime)
                }
            }
        }
    }

    pluginManager.withPlugin("com.android.library") {
        dependencies {
            glanceLibraries(libs, composeBom)
        }
    }

    pluginManager.withPlugin("com.android.application") {
        dependencies {
            glanceLibraries(libs, composeBom)
        }
    }
}
