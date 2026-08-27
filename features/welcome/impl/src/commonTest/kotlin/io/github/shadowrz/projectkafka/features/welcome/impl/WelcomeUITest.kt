package io.github.shadowrz.projectkafka.features.welcome.impl

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertNeverCalled
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertableCallback
import io.github.shadowrz.projectkafka.tests.utils.callback.shouldBeCalled
import io.kotest.core.spec.style.FreeSpec
import org.jetbrains.compose.resources.getString
import projectkafka.features.welcome.impl.generated.resources.Res
import projectkafka.features.welcome.impl.generated.resources.welcome_quickstart

@OptIn(ExperimentalTestApi::class)
class WelcomeUITest :
    FreeSpec({
        "clicking on quickstart invokes onQuickStart callback" {
            runComposeUiTest {
                val callback = AssertableCallback()

                setWelcomeView(onQuickStart = callback)

                onNodeWithText(getString(Res.string.welcome_quickstart)).performClick()
                callback.shouldBeCalled()
            }
        }
    })

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.setWelcomeView(onQuickStart: () -> Unit = AssertNeverCalled()) {
    setContent {
        WelcomeUI(onQuickStart = onQuickStart)
    }
}
