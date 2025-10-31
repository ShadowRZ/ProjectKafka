package io.github.shadowrz.projectkafka.codegen

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.ClassKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Origin
import io.github.shadowrz.projectkafka.annotations.ContributesComponent

class ContributesComponentSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation(ContributesComponent::class.qualifiedName!!)
        val (valid, invalid) = symbols.partition { it.validate() }

        if (valid.isEmpty()) return invalid

        valid.forEach {
            if (it is KSClassDeclaration) generateComponentAssistedFactory(it)
            if (it is KSFunctionDeclaration) generateComponentUIBinding(it, Symbols(resolver))
        }

        return invalid
    }

    @OptIn(KspExperimental::class)
    private fun generateComponentAssistedFactory(klass: KSClassDeclaration) {
        val packageName = klass.containingFile!!.packageName.asString()
        val className = "${klass.simpleName.asString()}_AssistedFactory"
        val contributionAnnotations = klass.getKSAnnotationsByType(ContributesComponent::class)
        val primaryConstructor = klass.primaryConstructor!!
        val assistedParamters = primaryConstructor.parameters.filter { it.isAnnotationPresent(Assisted::class) }

        if (assistedParamters.size != 3) {
            error(
                "${klass.qualifiedName?.asString()} must have a primary constructor with exactly 3 @Assisted paramters," +
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
                        addSuperinterface(Symbols.Names.GenericComponentFactory.plusParameter(assistedParamters[0].type.toTypeName()))
                        addAnnotation(AnnotationSpec.builder(Origin::class).addMember("%T::class", klass.toClassName()).build())
                        addAnnotation(AnnotationSpec.builder(ClassKey::class).addMember("%T::class", klass.toClassName()).build())
                        contributionAnnotations.forEach { annotation ->
                            val scope = annotation.arguments.single { it.name?.asString() == "scope" }.value as KSType

                            addAnnotation(
                                AnnotationSpec
                                    .builder(ContributesIntoMap::class)
                                    .addMember("scope = %T::class", scope.toTypeName())
                                    .addMember("binding = %L", GenericComponentFactoryBindingAnnotation)
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
                                                Symbols.Names.GenericComponent
                                                    .plusParameter(STAR)
                                                    .copy(nullable = true),
                                            ).build(),
                                        ParameterSpec
                                            .builder(
                                                "plugins",
                                                List::class.asClassName().plusParameter(Symbols.Names.Plugin),
                                            ).build(),
                                    ),
                                ).returns(klass.toClassName())
                                .addModifiers(KModifier.ABSTRACT, KModifier.OVERRIDE)
                                .build(),
                        )
                    }.build(),
            ).build()
            .writeTo(codeGenerator, dependencies = Dependencies(true, klass.containingFile!!))
    }

    private fun generateComponentUIBinding(
        function: KSFunctionDeclaration,
        symbols: Symbols,
    ) {
        val packageName = function.containingFile!!.packageName.asString()
        val className = "${function.simpleName.asString()}_ComponentUI"
        val contributionAnnotations = function.getKSAnnotationsByType(ContributesComponent::class)

        if (function.parameters.size != 2) {
            error(
                "${function.qualifiedName?.asString()} must have exactly 2 @Assisted paramters. current: ${function.parameters.size}",
            )
        }

        function.parameters[0].let {
            if (it.name?.asString() != "component") {
                error(
                    "${function.qualifiedName?.asString()}'s first paramter must named 'component'.",
                )
            }
        }

        val componentType = function.parameters[0].type.toTypeName()

        function.parameters[1].let {
            if (it.name?.asString() != "modifier") {
                error(
                    "${function.qualifiedName?.asString()}'s second paramter must named 'modifier'.",
                )
            }

            val resolvedType = it.type.resolve()
            if (!symbols.modifier.isAssignableFrom(resolvedType)) {
                error(
                    "${function.qualifiedName?.asString()}'s second paramter must be of type 'Modifier'.",
                )
            }
        }

        FileSpec
            .builder(packageName, className)
            .addType(
                TypeSpec
                    .classBuilder(className)
                    .apply {
                        addSuperinterface(Symbols.Names.ComponentUI.plusParameter(componentType))
                        addAnnotation(AnnotationSpec.builder(Origin::class).addMember("%T::class", componentType).build())
                        addAnnotation(AnnotationSpec.builder(ClassKey::class).addMember("%T::class", componentType).build())
                        contributionAnnotations.forEach { annotation ->
                            val scope = annotation.arguments.single { it.name?.asString() == "scope" }.value as KSType

                            addAnnotation(
                                AnnotationSpec
                                    .builder(ContributesIntoMap::class)
                                    .addMember("scope = %T::class", scope.toTypeName())
                                    .addMember("binding = %L", ComponentUIFactoryBindingAnnotation)
                                    .build(),
                            )
                        }

                        addAnnotation(Inject::class)

                        addFunction(
                            FunSpec
                                .builder("Content")
                                .addModifiers(KModifier.OVERRIDE)
                                .addAnnotation(Symbols.Names.Composable)
                                .addParameters(
                                    listOf(
                                        ParameterSpec.builder("component", componentType).build(),
                                        ParameterSpec.builder("modifier", Symbols.Names.Modifier).build(),
                                    ),
                                ).addStatement(
                                    "return %M(component = component, modifier = modifier)",
                                    MemberName(packageName, function.simpleName.getShortName()),
                                ).build(),
                        )
                    }.build(),
            ).build()
            .writeTo(codeGenerator, dependencies = Dependencies(true, function.containingFile!!))
    }

    private companion object {
        val GenericComponentFactoryBindingAnnotation = AnnotationSpec
            .builder(
                Symbols.Names.binding.plusParameter(
                    Symbols.Names.GenericComponentFactory.plusParameter(STAR),
                ),
            ).build()
        val ComponentUIFactoryBindingAnnotation = AnnotationSpec
            .builder(
                Symbols.Names.binding.plusParameter(
                    Symbols.Names.ComponentUI.plusParameter(STAR),
                ),
            ).build()
    }
}
