package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import io.github.shadowrz.projectkafka.designsystem.pages.SmallTopBarPage
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_open_source_licenses
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LicensesUI(
    state: LicensesState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    SmallTopBarPage(
        modifier = modifier.fillMaxSize(),
        onBack = onBack,
        title = stringResource(CommonStrings.common_open_source_licenses),
    ) { innerPadding ->
        LibrariesContainer(
            libraries = state.libraries,
            footer = {
                item {
                    Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars))
                }
            },
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        )
    }
}

@Composable
@PreviewLightDark
@PreviewDynamicColors
internal fun PreviewLicensesUI(@PreviewParameter(LicensesStateProvider::class) state: LicensesState) = KafkaPreview {
    LicensesUI(
        state = state,
        onBack = {},
    )
}
