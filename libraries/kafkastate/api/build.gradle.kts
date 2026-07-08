plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.hanekokoro.framework.markers)
            api(libs.hanekokoro.framework.runtime.presenter)
            api(project(":libraries:core"))
            api(project(":libraries:data:api"))
        }

        remove(commonTest.get())
        remove(jvmTest.get())
    }
}
