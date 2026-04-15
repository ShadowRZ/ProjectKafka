package io.github.shadowrz.projectkafka.libraries.kafkaui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.ModalBottomSheet
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.Globe
import io.github.shadowrz.projectkafka.designsystem.icons.OpenInNew
import org.jetbrains.compose.resources.stringResource
import projectkafka.libraries.kafkaui.generated.resources.Res
import projectkafka.libraries.kafkaui.generated.resources.helpsheet_help
import projectkafka.libraries.kafkaui.generated.resources.helpsheet_plurality_resources

@Composable
fun KafkaHelpSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        HelpContent(
            onOpenExternalLink = { uriHandler.openUri(it) },
        )
    }
}

@Composable
internal fun HelpContent(
    onOpenExternalLink: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            stringResource(Res.string.helpsheet_help),
            style = KafkaTheme.typography.titleLarge,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                stringResource(Res.string.helpsheet_plurality_resources),
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp),
                style = KafkaTheme.typography.titleMedium,
                color = KafkaTheme.materialColors.primary,
            )

            ListItem(
                modifier =
                    Modifier.clickable {
                        onOpenExternalLink("https://wiki.pluralitycn.com")
                    },
                headlineContent = {
                    Text("PluralityCN Wiki")
                },
                supportingContent = {
                    Text("多意识体中文百科")
                },
                leadingContent = {
                    Icon(
                        KafkaIcons.Globe,
                        contentDescription = null,
                    )
                },
                trailingContent = {
                    Icon(
                        KafkaIcons.OpenInNew,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
