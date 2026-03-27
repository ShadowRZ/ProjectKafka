package io.github.shadowrz.projectkafka.features.about.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.icons.Code
import io.github.shadowrz.projectkafka.designsystem.icons.InfoOutline
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.app_name
import io.github.shadowrz.projectkafka.libraries.strings.common_about_named
import io.github.shadowrz.projectkafka.libraries.strings.common_open_source_licenses
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.about.impl.generated.resources.Res
import projectkafka.features.about.impl.generated.resources.about_app_description
import projectkafka.features.about.impl.generated.resources.about_source_code
import projectkafka.features.about.impl.generated.resources.about_unrelated_with_apache_kafka
import projectkafka.features.about.impl.generated.resources.about_unrelated_with_bhsr
import projectkafka.features.about.impl.generated.resources.about_version

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutUI(
    state: AboutState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onLicenses: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                titleStr = stringResource(
                    CommonStrings.common_about_named,
                    stringResource(
                        CommonStrings.app_name,
                    ),
                ),
                navigationIcon = {
                    BackButton(onClick = onBack)
                },
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
                    stringResource(Res.string.about_app_description),
                    color = KafkaTheme.materialColors.primary,
                    style = KafkaTheme.typography.titleMedium,
                )
                Text(
                    stringResource(Res.string.about_unrelated_with_bhsr),
                    color = KafkaTheme.materialColors.tertiary,
                    style = KafkaTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    stringResource(Res.string.about_unrelated_with_apache_kafka),
                    color = KafkaTheme.materialColors.tertiary,
                    style = KafkaTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column {
                ListItem(
                    onClick = {
                        state.eventSink(AboutEvents.OpenSourceCode)
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(Res.string.about_source_code),
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = KafkaIcons.Code,
                            contentDescription = null,
                        )
                    },
                )
                ListItem(
                    onClick = onLicenses,
                    headlineContent = {
                        Text(
                            text = stringResource(CommonStrings.common_open_source_licenses),
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = KafkaIcons.InfoOutline,
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
                        Res.string.about_version,
                        state.buildMeta.applicationName,
                        state.buildMeta.versionName,
                        state.buildMeta.versionCode,
                    ),
                    style = KafkaTheme.typography.labelSmall,
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
