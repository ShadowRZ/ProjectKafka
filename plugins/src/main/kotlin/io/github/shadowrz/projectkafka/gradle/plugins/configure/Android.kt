package io.github.shadowrz.projectkafka.gradle.plugins.configure

import com.android.build.api.dsl.CommonExtension
import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.KafkaProperties
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
internal fun Project.configureAndroid(kafkaProperties: KafkaProperties) {
    val desugar = libs.findLibrary("desugar").get()
    val javaVersion = kafkaProperties.jvmTarget.map(JavaVersion::toVersion)

    pluginManager.withPlugin(PluginIds.AGP_BASE) {
        extensions.configure(CommonExtension::class.java) { android ->
            android.compileSdk = kafkaProperties.compileSdk.get()
            android.defaultConfig.apply {
                minSdk = kafkaProperties.minSdk.get()
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                vectorDrawables {
                    useSupportLibrary = true
                    generatedDensities()
                }
            }

            android.compileOptions.apply {
                sourceCompatibility = javaVersion.get()
                targetCompatibility = javaVersion.get()
                isCoreLibraryDesugaringEnabled = true
            }

            android.testOptions.unitTests.isIncludeAndroidResources = true
            android.testOptions.unitTests.isReturnDefaultValues = true
            android.testOptions.unitTests.all { it.useJUnitPlatform() }

            android.packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

        dependencies.add(ConfigurationNames.CORE_LIBRARY_DESUGARING, desugar)
    }
}
