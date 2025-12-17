package io.github.shadowrz.projectkafka.features.about.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ArrowBack
import io.github.shadowrz.projectkafka.libraries.icons.material.Code
import io.github.shadowrz.projectkafka.libraries.icons.material.InfoOutline
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutUI(
    state: AboutState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onLicenses: () -> Unit = {},
) {
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors =
                    topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {
                    Text(
                        text =
                            stringResource(
                                CommonStrings.common_about_named,
                                stringResource(
                                    CommonStrings.app_name,
                                ),
                            ),
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
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(vertical = 8.dp)
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    stringResource(R.string.about_app_description),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    stringResource(R.string.about_unrelated_with_bhsr),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    stringResource(R.string.about_unrelated_with_apache_kafka),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column {
                ListItem(
                    modifier =
                        Modifier.clickable(onClick = {
                            state.eventSink(AboutEvents.OpenSourceCode)
                        }),
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.about_source_code),
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = MaterialIcons.Code,
                            contentDescription = null,
                        )
                    },
                )
                ListItem(
                    modifier =
                        Modifier.clickable(onClick = onLicenses),
                    headlineContent = {
                        Text(
                            text = stringResource(CommonStrings.common_open_source_licenses),
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = MaterialIcons.InfoOutline,
                            contentDescription = null,
                        )
                    },
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    stringResource(
                        R.string.about_version,
                        state.buildMeta.applicationName,
                        state.buildMeta.versionName,
                        state.buildMeta.versionCode,
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                )
            }
        }
    }
}

@Composable
@NonRestartableComposable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun AboutUI(
    component: AboutComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    AboutUI(
        state = state,
        modifier = modifier,
        onBack = component::navigateUp,
        onLicenses = component.callback::onLicenses,
    )
}
