plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.kotest")
    id("com.android.kotlin.multiplatform.library")
    alias(libs.plugins.metro)
    alias(libs.plugins.sqldelight)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.data.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.data.api)
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            implementation(projects.libraries.core)
            implementation(projects.libraries.di)
            implementation(libs.androidx.paging.common)
            implementation(libs.kotlinx.datetime)
            implementation(libs.okio)
            implementation(libs.sqldelight.coroutines)
        }

        jvmMain.dependencies {
            implementation(libs.sqldelight.jvm)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.sqlite.framework)
            implementation(libs.sqldelight.android)
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
    }
}

sqldelight {
    databases {
        create("GlobalDatabase") {
            packageName.set("io.github.shadowrz.projectkafka.libraries.data.impl.db")
            srcDirs.setFrom("src/commonMain/sqldelight/global")
            verifyMigrations = true
            deriveSchemaFromMigrations = true
        }
        create("SystemDatabase") {
            packageName.set("io.github.shadowrz.projectkafka.libraries.data.impl.db")
            srcDirs.setFrom("src/commonMain/sqldelight/system")
            verifyMigrations = true
            deriveSchemaFromMigrations = true
        }
    }
}
