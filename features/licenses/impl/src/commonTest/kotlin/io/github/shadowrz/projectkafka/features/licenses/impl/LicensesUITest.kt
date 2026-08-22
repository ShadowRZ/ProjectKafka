package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertNeverCalled
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertableCallback
import io.github.shadowrz.projectkafka.tests.utils.callback.shouldBeCalled
import io.github.shadowrz.projectkafka.tests.utils.uitest.pressBack
import io.kotest.core.spec.style.FreeSpec

@OptIn(ExperimentalTestApi::class)
class LicensesUITest :
    FreeSpec({
        "clicking on back invokes onBack callback" {
            runComposeUiTest {
                val callback = AssertableCallback()

                setLicensesUI(
                    state = aLicensesState(),
                    onBack = callback,
                )

                pressBack()
                callback.shouldBeCalled()
            }
        }
    })

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.setLicensesUI(
    state: LicensesState,
    onBack: () -> Unit = AssertNeverCalled(),
) {
    setContent {
        LicensesUI(
            state = state,
            onBack = onBack,
        )
    }
}
