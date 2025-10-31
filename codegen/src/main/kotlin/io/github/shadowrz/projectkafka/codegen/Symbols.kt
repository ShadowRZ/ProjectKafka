package io.github.shadowrz.projectkafka.codegen

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ClassName

internal class Symbols(
    resolver: Resolver,
) {
    val modifier = resolver.loadKSType(Names.Modifier.canonicalName)

    private fun Resolver.loadKSType(name: String): KSType = loadOptionalKSType(name) ?: error("Could not find $name in classpath")

    private fun Resolver.loadOptionalKSType(name: String?): KSType? {
        if (name == null) return null
        return getClassDeclarationByName(getKSNameFromString(name))?.asType(emptyList())
    }

    object Names {
        val ComponentKey = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "ComponentKey")
        val GenericComponent = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "GenericComponent")
        val GenericComponentFactory = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "GenericComponent", "Factory")
        val binding = ClassName("dev.zacsweers.metro", "binding")
        val Modifier = ClassName("androidx.compose.ui", "Modifier")
        val Composable = ClassName("androidx.compose.runtime", "Composable")
        val Plugin = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "Plugin")
        val ComponentUI = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "ComponentUI")
    }
}
