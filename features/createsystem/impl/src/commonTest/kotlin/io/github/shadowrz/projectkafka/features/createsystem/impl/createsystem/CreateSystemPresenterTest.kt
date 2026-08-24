package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import io.github.shadowrz.projectkafka.tests.utils.test
import io.github.shadowrz.projectkafka.tests.utils.warmUpMolecule
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class CreateSystemPresenterTest :
    FreeSpec({
        warmUpMolecule()

        "presenter - initial state" {
            runTest {
                presenter().test {
                    val state = awaitItem()
                    assertSoftly(state) {
                        textFieldState.text shouldBe ""
                        valid.shouldBeFalse()
                    }
                }
            }
        }

        "presenter - when system name is non empty, valid should be true" {
            runTest {
                presenter().test {
                    var state = awaitItem()
                    assertSoftly(state) {
                        textFieldState.text shouldBe ""
                        valid.shouldBeFalse()
                    }
                    state.textFieldState.setTextAndPlaceCursorAtEnd("???? System")
                    state = awaitItem()
                    assertSoftly(state) {
                        textFieldState.text shouldBe "???? System"
                        valid.shouldBeTrue()
                    }
                }
            }
        }
    })

private fun TestScope.presenter(): CreateSystemPresenter {
    val presenter = CreateSystemPresenter()

    return presenter
}
