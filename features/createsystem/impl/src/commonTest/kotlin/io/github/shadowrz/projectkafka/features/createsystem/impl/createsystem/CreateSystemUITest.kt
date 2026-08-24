package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_continue
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertNeverCalledWithParam
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertableCallbackWithParam
import io.github.shadowrz.projectkafka.tests.utils.callback.shouldBeCalledOnceWith
import io.kotest.core.spec.style.FreeSpec
import org.jetbrains.compose.resources.getString

@OptIn(ExperimentalTestApi::class)
class CreateSystemUITest :
    FreeSpec({
        "when valid is true, continue button should be enabled" {
            runComposeUiTest {
                val callback = AssertNeverCalledWithParam<String>()

                setCreateSystemView(
                    state = aCreateSystemState(),
                    onContinue = callback,
                )

                onNodeWithText(getString(CommonStrings.common_continue)).assertIsEnabled()
            }
        }

        "when valid is false, continue button should be disabled" {
            runComposeUiTest {
                val callback = AssertNeverCalledWithParam<String>()

                setCreateSystemView(
                    state = aCreateSystemState(valid = false),
                    onContinue = callback,
                )

                onNodeWithText(getString(CommonStrings.common_continue)).assertIsNotEnabled()
            }
        }

        "when valid is true, clicking continue should invoke onContinue callback" {
            runComposeUiTest {
                val callback = AssertableCallbackWithParam<String>()

                setCreateSystemView(
                    state = aCreateSystemState(initialText = "???? System"),
                    onContinue = callback,
                )

                onNodeWithText(getString(CommonStrings.common_continue)).apply {
                    assertIsEnabled()
                    performClick()
                }

                callback.shouldBeCalledOnceWith("???? System")
            }
        }
    })

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.setCreateSystemView(
    state: CreateSystemState,
    onContinue: (String) -> Unit = AssertNeverCalledWithParam(),
) {
    setContent {
        CreateSystemUI(
            state = state,
            onContinue = onContinue,
        )
    }
}
