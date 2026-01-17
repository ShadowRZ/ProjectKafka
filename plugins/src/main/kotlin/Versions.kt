import org.gradle.api.JavaVersion
import org.gradle.jvm.toolchain.JavaLanguageVersion

object Versions {
    const val COMPILE_SDK = 36
    const val MIN_SDK = 23
    const val TARGET_SDK = 36

    private const val JAVA_VERSION = 25

    val javaVersion = JavaVersion.toVersion(JAVA_VERSION)
    val javaLanguageVersion = JavaLanguageVersion.of(JAVA_VERSION)

    // App version
    const val VERSION_CODE = 1

    @Suppress("detekt:MayBeConst")
    val VERSION_NAME = "1.0"
}
