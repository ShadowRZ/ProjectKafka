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
        register("android") {
            id = "io.github.shadowrz.projectkafka.android"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.AndroidPlugin"
        }
        register("kotlin") {
            id = "io.github.shadowrz.projectkafka.kotlin"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.KotlinPlugin"
        }
        register("library") {
            id = "io.github.shadowrz.projectkafka.library"
            implementationClass = "io.github.shadowrz.projectkafka.gradle.plugins.LibraryPlugin"
        }
        register("jvmLibrary") {
            id = "io.github.shadowrz.projectkafka.jvm-library"
            implementationClass = "io.github.shadowrz.projectkafka.gradle.plugins.JvmLibraryPlugin"
        }
        register("application") {
            id = "io.github.shadowrz.projectkafka.application"
            implementationClass = "io.github.shadowrz.projectkafka.gradle.plugins.ApplicationPlugin"
        }
        register("compose") {
            id = "io.github.shadowrz.projectkafka.compose"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.ComposePlugin"
        }
        register("kotest") {
            id = "io.github.shadowrz.projectkafka.kotest"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.KotestPlugin"
        }
        register("glance") {
            id = "io.github.shadowrz.projectkafka.glance"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.GlancePlugin"
        }
        register("multiplatform") {
            id = "io.github.shadowrz.projectkafka.multiplatform"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.MultiplatformPlugin"
        }
        register("kafkaApplication") {
            id = "io.github.shadowrz.projectkafka.application.projectkafka"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.KafkaApplicationPlugin"
        }
        register("codestyle") {
            id = "io.github.shadowrz.projectkafka.codestyle"
            implementationClass =
                "io.github.shadowrz.projectkafka.gradle.plugins.CodestylePlugin"
        }
        register("feature") {
            id = "io.github.shadowrz.projectkafka.feature"
            implementationClass = "io.github.shadowrz.projectkafka.gradle.plugins.FeaturePlugin"
        }
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.compose.compiler.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.kotlin.parcelize.plugin)
    implementation(libs.compose.multiplatform.plugin)
    implementation(libs.detekt.plugin)
    implementation(libs.kotest.plugin)
    implementation(libs.ksp.plugin)
    implementation(libs.ktlint.plugin)
    implementation(libs.metro.gradle.plugin)
}
