package io.github.shadowrz.projectkafka.features.quickstart.impl

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_data_management
import io.github.shadowrz.projectkafka.libraries.strings.common_help
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertNeverCalled
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertableCallback
import io.github.shadowrz.projectkafka.tests.utils.callback.shouldBeCalled
import io.github.shadowrz.projectkafka.tests.utils.uitest.pressBack
import io.kotest.core.spec.style.FreeSpec
import org.jetbrains.compose.resources.getString
import projectkafka.features.quickstart.impl.generated.resources.Res
import projectkafka.features.quickstart.impl.generated.resources.quickstart_createsystem_title
import projectkafka.features.quickstart.impl.generated.resources.quickstart_resources_title

@OptIn(ExperimentalTestApi::class)
class QuickStartUITest :
    FreeSpec({
        "clicking on create system invokes onCreateSystem callback" {
            runComposeUiTest {
                val callback = AssertableCallback()

                setQuickStartView(onCreateSystem = callback)

                onNodeWithText(getString(Res.string.quickstart_createsystem_title)).performClick()
                callback.shouldBeCalled()
            }
        }

        "clicking on create system invokes onDataManage callback" {
            runComposeUiTest {
                val callback = AssertableCallback()

                setQuickStartView(onDataManage = callback)

                onNodeWithText(getString(CommonStrings.common_data_management)).performClick()
                callback.shouldBeCalled()
            }
        }

        "clicking on create system makes help sheet visible" {
            runComposeUiTest {
                setQuickStartView()

                onNodeWithText(getString(Res.string.quickstart_resources_title)).performClick()
                onNodeWithText(getString(CommonStrings.common_help)).assertExists()
            }
        }

        "clicking on back invokes onBack callback" {
            runComposeUiTest {
                val callback = AssertableCallback()

                setQuickStartView(onBack = callback)

                pressBack()
                callback.shouldBeCalled()
            }
        }
    })

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.setQuickStartView(
    onCreateSystem: () -> Unit = AssertNeverCalled(),
    onDataManage: () -> Unit = AssertNeverCalled(),
    onBack: () -> Unit = AssertNeverCalled(),
) {
    setContent {
        QuickStartUI(
            onCreateSystem = onCreateSystem,
            onDataManage = onDataManage,
            onBack = onBack,
        )
    }
}
