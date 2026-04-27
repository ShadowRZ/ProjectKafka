plugins {
    alias(libs.plugins.projectkafka.multiplatform)
}

kotlin {
    jvm()

    sourceSets {

        commonMain.dependencies {
            api(project(":libraries:architecture"))
            api(project(":libraries:data:api"))
        }

        remove(commonTest.get())
    }
}
