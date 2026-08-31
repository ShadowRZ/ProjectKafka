plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            api(libs.androidx.lifecycle.runtime.testing)
            api(libs.androidx.navigationevent.compose)
            api(libs.androidx.navigationevent.testing)
            api(libs.compose.ui.test)
            api(libs.kotest.assertions)
            api(libs.kotest.framework.engine)
            api(libs.hanekokoro.framework.markers)
            api(libs.hanekokoro.framework.runtime.presenter)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(project(":libraries:strings"))
        }

        remove(commonTest.get())
    }
}
