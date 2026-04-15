package io.github.shadowrz.projectkafka.features.quickstart.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.ElevatedCard
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.ListIcon
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.DatabaseOutline
import io.github.shadowrz.projectkafka.designsystem.icons.GroupAddOutline
import io.github.shadowrz.projectkafka.designsystem.icons.HelpOutline
import io.github.shadowrz.projectkafka.designsystem.pages.FlowStepPage
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.kafkaui.KafkaHelpSheet
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_data_management
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.quickstart.impl.generated.resources.Res
import projectkafka.features.quickstart.impl.generated.resources.quickstart_createsystem_description
import projectkafka.features.quickstart.impl.generated.resources.quickstart_createsystem_title
import projectkafka.features.quickstart.impl.generated.resources.quickstart_datamanage_description
import projectkafka.features.quickstart.impl.generated.resources.quickstart_description
import projectkafka.features.quickstart.impl.generated.resources.quickstart_resources_description
import projectkafka.features.quickstart.impl.generated.resources.quickstart_resources_title
import projectkafka.features.quickstart.impl.generated.resources.quickstart_title

@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun QuickStartUI(
    component: QuickStartComponent,
    modifier: Modifier = Modifier,
) {
    QuickStartUI(
        modifier = modifier,
        onCreateSystem = {
            component.callback.onCreateSystem()
        },
        onDataManage = {
            component.callback.onDataManage()
        },
        onBack = component::navigateUp,
    )
}

@Composable
fun QuickStartUI(
    modifier: Modifier = Modifier,
    onCreateSystem: () -> Unit = {},
    onDataManage: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    var showHelpSheet by rememberSaveable { mutableStateOf(false) }

    FlowStepPage(
        modifier = modifier,
        title = stringResource(Res.string.quickstart_title),
        subtitle = stringResource(Res.string.quickstart_description),
        onBack = onBack,
    ) {
        Column(
            modifier = Modifier.padding(top = 48.dp).widthIn(max = 480.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ElevatedCard {
                ListItem(
                    onClick = onCreateSystem,
                    headlineContent = {
                        Text(stringResource(Res.string.quickstart_createsystem_title))
                    },
                    supportingContent = {
                        Text(stringResource(Res.string.quickstart_createsystem_description))
                    },
                    leadingContent = {
                        ListIcon(
                            KafkaIcons.GroupAddOutline,
                            contentDescription = null,
                        )
                    },
                )
            }
            ElevatedCard {
                ListItem(
                    onClick = onDataManage,
                    headlineContent = {
                        Text(stringResource(CommonStrings.common_data_management))
                    },
                    supportingContent = {
                        Text(stringResource(Res.string.quickstart_datamanage_description))
                    },
                    leadingContent = {
                        ListIcon(
                            KafkaIcons.DatabaseOutline,
                            contentDescription = null,
                        )
                    },
                )
            }
            ElevatedCard {
                ListItem(
                    onClick = { showHelpSheet = true },
                    headlineContent = {
                        Text(stringResource(Res.string.quickstart_resources_title))
                    },
                    supportingContent = {
                        Text(stringResource(Res.string.quickstart_resources_description))
                    },
                    leadingContent = {
                        ListIcon(
                            KafkaIcons.HelpOutline,
                            contentDescription = null,
                        )
                    },
                )
            }
        }
    }

    if (showHelpSheet) {
        KafkaHelpSheet(
            onDismissRequest = { showHelpSheet = false },
        )
    }
}

@Composable
@PreviewLightDark
@PreviewDynamicColors
internal fun PreviewQuickStartUI() =
    KafkaPreview {
        QuickStartUI(
            onBack = {},
            onDataManage = {},
            onCreateSystem = {},
        )
    }
