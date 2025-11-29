package io.github.shadowrz.projectkafka.tests.utils

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.kotest.core.listeners.BeforeContainerListener
import io.kotest.core.spec.Extendable
import io.kotest.core.test.TestCase
import kotlinx.coroutines.test.runTest
import kotlin.time.Duration.Companion.seconds

class WarmUpMolecule : BeforeContainerListener {
    override suspend fun beforeContainer(testCase: TestCase) {
        runTest(timeout = 60.seconds) {
            moleculeFlow(RecompositionMode.Immediate) {}.test {
                awaitItem()
            }
        }
        super.beforeContainer(testCase)
    }
}

fun Extendable.warmUpMolecule() = extensions(WarmUpMolecule())
