plugins {
    alias(libs.plugins.projectkafka.feature)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.about.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:about:api"))
            implementation(project(":buildmeta"))
            implementation(project(":designsystem"))
            implementation(project(":features:licenses:api"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:strings"))
        }

        remove(commonTest.get())
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
