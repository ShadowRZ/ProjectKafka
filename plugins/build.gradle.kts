import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "io.github.shadowrz.projectkafka.plugins"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

repositories {
    google {
        content {
            includeGroupByRegex("com\\.android.*")
            includeGroupByRegex("com\\.google.*")
            includeGroupByRegex("androidx.*")
        }
    }
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("libraryPlugin") {
            id = "io.github.shadowrz.projectkafka.library"
            implementationClass = "io.github.shadowrz.projectkafka.gradle.plugins.LibraryPlugin"
        }
        register("jvmLibraryPlugin") {
            id = "io.github.shadowrz.projectkafka.jvm-library"
            implementationClass = "io.github.shadowrz.projectkafka.gradle.plugins.JvmLibraryPlugin"
        }
        register("applicationPlugin") {
            id = "io.github.shadowrz.projectkafka.application"
            implementationClass = "io.github.shadowrz.projectkafka.gradle.plugins.ApplicationPlugin"
        }
        register("composeModulePlugin") {
            id = "io.github.shadowrz.projectkafka.compose-module"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.ComposeModulePlugin"
        }
        register("glanceModulePlugin") {
            id = "io.github.shadowrz.projectkafka.glance-module"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.GlanceModulePlugin"
        }
        register("metroModulePlugin") {
            id = "io.github.shadowrz.projectkafka.metro-module"
            implementationClass = "io.github.shadowrz.projectkafka.gradle.plugins.MetroModulePlugin"
        }
        register("multiplatformModulePlugin") {
            id = "io.github.shadowrz.projectkafka.multiplatform-module"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.MultiplatformModulePlugin"
        }
        register("kafkaApplicationPlugin") {
            id = "io.github.shadowrz.projectkafka.kafka-application"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.KafkaApplicationPlugin"
        }
        register("codestylePlugin") {
            id = "io.github.shadowrz.projectkafka.codestyle"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.CodestylePlugin"
        }
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.compose.compiler.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.kotlin.parcelize.plugin)
    implementation(libs.compose.multiplatform.plugin)
    implementation(libs.dependencyanalysis.plugin)
    implementation(libs.detekt.plugin)
    implementation(libs.ktlint.plugin)
    implementation(libs.metro.gradle.plugin)
}
