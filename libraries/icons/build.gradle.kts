import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.compose-module")
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
