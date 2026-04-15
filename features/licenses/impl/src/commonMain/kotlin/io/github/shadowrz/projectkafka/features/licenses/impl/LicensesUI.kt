package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_open_source_licenses
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LicensesUI(
    state: LicensesState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                titleStr = stringResource(CommonStrings.common_open_source_licenses),
                navigationIcon = {
                    BackButton(onClick = onBack)
                },
            )
        },
        contentWindowInsets =
            WindowInsets.systemBars.exclude(
                WindowInsets.navigationBars.only(WindowInsetsSides.Vertical),
            ),
    ) { innerPadding ->
        LibrariesContainer(
            libraries = state.libraries,
            footer = {
                item {
                    Spacer(
                        modifier =
                            Modifier.windowInsetsPadding(WindowInsets.navigationBars),
                    )
                }
            },
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        )
    }
}

@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun LicensesUI(
    component: LicensesComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    LicensesUI(
        state = state,
        modifier = modifier,
        onBack = component::navigateUp,
    )
}
