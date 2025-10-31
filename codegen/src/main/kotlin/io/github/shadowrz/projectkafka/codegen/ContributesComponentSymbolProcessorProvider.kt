package io.github.shadowrz.projectkafka.codegen

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class ContributesComponentSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        ContributesComponentSymbolProcessor(codeGenerator = environment.codeGenerator, logger = environment.logger)
}
