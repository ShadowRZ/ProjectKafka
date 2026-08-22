package io.github.shadowrz.projectkafka.features.about.impl

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_open_source_licenses
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertNeverCalled
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertNeverCalledWithParam
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertableCallback
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertableCallbackWithParam
import io.github.shadowrz.projectkafka.tests.utils.callback.shouldBeCalled
import io.github.shadowrz.projectkafka.tests.utils.callback.shouldBeCalledOnceWith
import io.github.shadowrz.projectkafka.tests.utils.uitest.pressBack
import io.kotest.core.spec.style.FreeSpec
import org.jetbrains.compose.resources.getString
import projectkafka.features.about.impl.generated.resources.Res
import projectkafka.features.about.impl.generated.resources.about_source_code

@OptIn(ExperimentalTestApi::class)
class AboutUITest :
    FreeSpec({
        "clicking on back invokes onBack callback" {
            runComposeUiTest {
                val callback = AssertableCallback()

                setAboutView(
                    state = anAboutState(),
                    onBack = callback,
                )

                pressBack()
                callback.shouldBeCalled()
            }
        }

        "clicking on open source code invokes onLink callback" {
            runComposeUiTest {
                val callback = AssertableCallbackWithParam<String>()

                setAboutView(
                    state = anAboutState(),
                    onLink = callback,
                )

                onNodeWithText(getString(Res.string.about_source_code)).performClick()
                callback.shouldBeCalledOnceWith(AboutLinks.SOURCE_CODE)
            }
        }

        "clicking on open source licenses invokes onLicenses callback" {
            runComposeUiTest {
                val callback = AssertableCallback()

                setAboutView(
                    state = anAboutState(),
                    onLicenses = callback,
                )

                onNodeWithText(getString(CommonStrings.common_open_source_licenses)).performClick()
                callback.shouldBeCalled()
            }
        }
    })

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.setAboutView(
    state: AboutState,
    onBack: () -> Unit = AssertNeverCalled(),
    onLicenses: () -> Unit = AssertNeverCalled(),
    onLink: (String) -> Unit = AssertNeverCalledWithParam(),
) {
    setContent {
        AboutUI(
            state = state,
            onBack = onBack,
            onLicenses = onLicenses,
            onLink = onLink,
        )
    }
}
