plugins {
    alias(libs.plugins.projectkafka.library.jvm)
}

dependencies {
    api(project(":libraries:data:api"))
}
