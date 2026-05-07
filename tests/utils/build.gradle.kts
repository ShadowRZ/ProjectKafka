plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            api(libs.androidx.navigationevent.testing)
            api(libs.kotest.framework.engine)
            api(libs.hanekokoro.framework.markers)
            api(libs.hanekokoro.framework.runtime.presenter)
            api(libs.androidx.navigationevent.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
        }

        remove(commonTest.get())
    }
}
