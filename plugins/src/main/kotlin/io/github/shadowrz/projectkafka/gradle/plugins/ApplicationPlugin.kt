package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.ApplicationExtension
import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyCodestyle
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureAndroid
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.AGP_APPLICATION)

            applyCodestyle()
            configureAndroid()
            configureKotlin()

            extensions.configure(ApplicationExtension::class.java) { application ->
                application.signingConfigs.configureEach { signingConfig ->
                    signingConfig.enableV3Signing = true
                    signingConfig.enableV4Signing = true
                }
            }
        }
    }
}
