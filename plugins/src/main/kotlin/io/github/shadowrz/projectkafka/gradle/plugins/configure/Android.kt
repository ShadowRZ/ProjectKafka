package io.github.shadowrz.projectkafka.gradle.plugins.configure

import com.android.build.api.dsl.CommonExtension
import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
internal fun Project.configureAndroid() {
    val desugar = libs.findLibrary("desugar").get()

    pluginManager.withPlugin(PluginIds.AGP_BASE) {
        extensions.configure(CommonExtension::class.java) {
            defaultConfig.apply {
                compileSdk = BuildMeta.COMPILE_SDK
                minSdk = BuildMeta.MIN_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                vectorDrawables {
                    useSupportLibrary = true
                    generatedDensities()
                }
            }

            compileOptions.apply {
                sourceCompatibility = BuildMeta.javaVersion
                targetCompatibility = BuildMeta.javaVersion
                isCoreLibraryDesugaringEnabled = true
            }

            testOptions.unitTests.all { it.useJUnitPlatform() }
        }

        dependencies.add(ConfigurationNames.CORE_LIBRARY_DESUGARING, desugar)
    }
}
