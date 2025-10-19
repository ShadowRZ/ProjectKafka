package io.github.shadowrz.projectkafka.tests.utils

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.time.Duration.Companion.seconds

class WarmUpRule : TestRule {
    companion object {
        init {
            warmUpMolecule()
        }
    }

    override fun apply(
        base: Statement?,
        description: Description?,
    ): Statement? = base
}

private fun warmUpMolecule() {
    runTest(timeout = 60.seconds) {
        moleculeFlow(RecompositionMode.Immediate) {}.test {
            awaitItem()
        }
    }
}
