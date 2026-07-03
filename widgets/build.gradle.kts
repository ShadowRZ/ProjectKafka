plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.widgets"
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.glance)
            implementation(libs.androidx.glance.appwidget)
            implementation(libs.androidx.glance.appwidget.preview)
            implementation(libs.androidx.glance.material3)
            implementation(libs.androidx.glance.preview)
        }
    }
}

dependencies {
    constraints {
        androidMainImplementation(libs.androidx.workmanager)
    }
}
