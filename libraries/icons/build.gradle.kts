import dev.detekt.gradle.Detekt

plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
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
