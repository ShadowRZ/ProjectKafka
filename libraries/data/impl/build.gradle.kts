plugins {
    // Blocked: https://github.com/sqldelight/sqldelight/issues/5914
    // id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.library")
    id("io.github.shadowrz.projectkafka.kotest")
    alias(libs.plugins.metro)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.data.impl"
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
    }
}

dependencies {
    // Common
    api(projects.libraries.data.api)
    implementation(projects.libraries.core)
    implementation(projects.libraries.di)
    implementation(libs.kotlinx.datetime)
    implementation(libs.okio)
    implementation(libs.sqldelight.coroutines)
    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.sqlite.framework)
    implementation(libs.sqldelight.android)
    // Testing
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.sqldelight.jvm)
}

sqldelight {
    databases {
        create("GlobalDatabase") {
            packageName.set("io.github.shadowrz.projectkakfa.libraries.data.impl.db")
            srcDirs.setFrom("src/main/sqldelight/global")
            schemaOutputDirectory.set(file("src/main/sqldelight/global/databases"))
            verifyMigrations = true
            deriveSchemaFromMigrations = true
        }
        create("SystemDatabase") {
            packageName.set("io.github.shadowrz.projectkakfa.libraries.data.impl.db")
            srcDirs.setFrom("src/main/sqldelight/system")
            schemaOutputDirectory.set(file("src/main/sqldelight/system/databases"))
            verifyMigrations = true
            deriveSchemaFromMigrations = true
        }
    }
}
