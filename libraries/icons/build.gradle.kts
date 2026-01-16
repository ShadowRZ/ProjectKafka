import dev.detekt.gradle.Detekt

plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
}

kotlin {
    jvm()
    sourceSets {
        remove(commonTest.get())
        remove(jvmTest.get())
    }
}

ktlint {
    filter {
        exclude("**/icons/**")
    }
}

tasks.withType<Detekt>().configureEach {
    exclude("**/icons/**")
}
