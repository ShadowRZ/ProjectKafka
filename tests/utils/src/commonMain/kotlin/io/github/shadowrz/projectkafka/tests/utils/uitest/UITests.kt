@file:OptIn(ExperimentalTestApi::class)

package io.github.shadowrz.projectkafka.tests.utils.uitest

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_back
import org.jetbrains.compose.resources.getString

suspend fun ComposeUiTest.pressBack() {
    val text = getString(CommonStrings.common_back)
    onNodeWithContentDescription(text).performClick()
}
