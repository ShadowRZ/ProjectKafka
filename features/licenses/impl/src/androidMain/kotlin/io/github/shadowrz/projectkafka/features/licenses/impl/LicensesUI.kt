package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ArrowBack
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@OptIn(ExperimentalMaterial3Api::class)
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
                title = {
                    Text(
                        stringResource(CommonStrings.common_open_source_licenses),
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = MaterialIcons.ArrowBack,
                            contentDescription = stringResource(CommonStrings.common_back),
                        )
                    }
                },
                colors =
                    topAppBarColors(
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
            )
        },
        contentWindowInsets =
            ScaffoldDefaults.contentWindowInsets.exclude(
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
