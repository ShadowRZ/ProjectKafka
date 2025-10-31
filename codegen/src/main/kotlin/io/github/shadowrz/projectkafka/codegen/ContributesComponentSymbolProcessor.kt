package io.github.shadowrz.projectkafka.codegen

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Origin
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.annotations.ContributesComponent

class ContributesComponentSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation(ContributesComponent::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()
        val (valid, invalid) = symbols.partition { it.validate() }

        if (valid.isEmpty()) return invalid

        valid.forEach { codeGenerator.generateAssistedFactory(it) }

        return invalid
    }

    @OptIn(KspExperimental::class)
    private fun CodeGenerator.generateAssistedFactory(klass: KSClassDeclaration) {
        val packageName = klass.containingFile!!.packageName.asString()
        val className = "${klass.simpleName.asString()}_AssistedFactory"
        val contributionAnnotations = klass.getKSAnnotationsByType(ContributesComponent::class)
        val primaryConstructor = klass.primaryConstructor!!
        val assistedParamters = primaryConstructor.parameters.filter { it.isAnnotationPresent(Assisted::class) }

        if (assistedParamters.size != 3) {
            error(
                "${klass.qualifiedName?.asString()} must have a primary constructor with 3 @Assisted paramters," +
                    "current: ${assistedParamters.size}",
            )
        }

        assistedParamters[0].let {
            if (it.name?.asString() != "componentContext") {
                error(
                    "${klass.qualifiedName?.asString()}'s first primary constructor param must named 'componentContext'.",
                )
            }
        }

        assistedParamters[1].let {
            if (it.name?.asString() != "parent") {
                error(
                    "${klass.qualifiedName?.asString()}'s second primary constructor param must named 'parent'.",
                )
            }
        }

        assistedParamters[2].let {
            if (it.name?.asString() != "plugins") {
                error(
                    "${klass.qualifiedName?.asString()}'s third primary constructor param must named 'plugins'.",
                )
            }
        }

        FileSpec
            .builder(packageName, className)
            .addType(
                TypeSpec
                    .interfaceBuilder(className)
                    .apply {
                        addSuperinterface(GenericComponentFactory.plusParameter(assistedParamters[0].type.toTypeName()))
                        addAnnotation(AnnotationSpec.builder(Origin::class).addMember("%T::class", klass.toClassName()).build())
                        addAnnotation(AnnotationSpec.builder(ComponentKey).addMember("%T::class", klass.toClassName()).build())
                        contributionAnnotations.forEach { annotation ->
                            val scope = annotation.arguments.single { it.name?.asString() == "scope" }.value as KSType

                            addAnnotation(
                                AnnotationSpec
                                    .builder(ContributesIntoMap::class)
                                    .addMember("scope = %T::class", scope.toTypeName())
                                    .addMember("binding = %L", BindingAnnotation)
                                    .build(),
                            )
                        }

                        addAnnotation(AssistedFactory::class)

                        addFunction(
                            FunSpec
                                .builder("create")
                                .addParameters(
                                    listOf(
                                        ParameterSpec
                                            .builder(
                                                "context",
                                                assistedParamters[0].type.toTypeName(),
                                            ).build(),
                                        ParameterSpec
                                            .builder(
                                                "parent",
                                                GenericComponent
                                                    .plusParameter(STAR)
                                                    .copy(nullable = true),
                                            ).build(),
                                        ParameterSpec
                                            .builder(
                                                "plugins",
                                                List::class.asClassName().plusParameter(Plugin),
                                            ).build(),
                                    ),
                                ).returns(klass.toClassName())
                                .addModifiers(KModifier.ABSTRACT, KModifier.OVERRIDE)
                                .build(),
                        )
                    }.build(),
            ).build()
            .writeTo(this, dependencies = Dependencies(true, klass.containingFile!!))
    }

    private companion object {
        val ComponentKey = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "ComponentKey")
        val GenericComponent = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "GenericComponent")
        val GenericComponentFactory = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "GenericComponent", "Factory")
        val BindingAnnotation = AnnotationSpec
            .builder(
                binding::class.asTypeName().plusParameter(
                    GenericComponentFactory.plusParameter(STAR),
                ),
            ).build()
        val Plugin = ClassName("io.github.shadowrz.projectkafka.libraries.architecture", "Plugin")
    }
}
