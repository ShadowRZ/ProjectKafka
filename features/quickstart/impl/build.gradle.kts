plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.quickstart.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:quickstart:api"))
            implementation(project(":designsystem"))
            implementation(project(":features:createsystem:api"))
            implementation(project(":features:datamanage:api"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:kafkaui"))
            implementation(project(":libraries:strings"))
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
