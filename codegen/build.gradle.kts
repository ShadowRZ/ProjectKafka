plugins {
    id("io.github.shadowrz.projectkafka.jvm-library")
}

dependencies {
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    implementation(libs.ksp.api)
    implementation(libs.metro.runtime)
    // Used for Annotation References
    implementation(projects.annotations)
}
