plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.about.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.about.api)
            implementation(projects.assets)
            implementation(projects.buildmeta)
            implementation(projects.features.licenses.api)
            implementation(projects.libraries.components)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }

        remove(commonTest.get())
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
