plugins {
    alias(libs.plugins.projectkafka.library.jvm)
}

dependencies {
    api(projects.libraries.data.api)
}
