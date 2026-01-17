import org.gradle.api.JavaVersion
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Versions {
    const val COMPILE_SDK = 36
    const val MIN_SDK = 23
    const val TARGET_SDK = 36

    const val JAVA_VERSION = 21

    val javaVersion = JavaVersion.toVersion(JAVA_VERSION)
    val jvmTarget = JvmTarget.fromTarget(JAVA_VERSION.toString())

    // App version
    const val VERSION_CODE = 1

    @Suppress("detekt:MayBeConst")
    val VERSION_NAME = "1.0"
}
