package io.github.shadowrz.projectkafka.features.share.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.icons.EventListOutline
import io.github.shadowrz.projectkafka.designsystem.icons.NoteStackOutline
import io.github.shadowrz.projectkafka.designsystem.icons.SwitchAccountOutline
import io.github.shadowrz.projectkafka.features.share.api.ShareData
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.share.impl.generated.resources.Res
import projectkafka.features.share.impl.generated.resources.share_new_activity
import projectkafka.features.share.impl.generated.resources.share_new_quick_note
import projectkafka.features.share.impl.generated.resources.share_new_switch_log
import projectkafka.features.share.impl.generated.resources.share_new_title
import projectkafka.features.share.impl.generated.resources.share_send_to_chat
import projectkafka.features.share.impl.generated.resources.share_title

@Composable
private fun ShareUI(
    shareData: ShareData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                titleStr = stringResource(Res.string.share_title),
                navigationIcon = {
                    BackButton(onClick = onBack)
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
        ) {
            item {
                Text(
                    stringResource(Res.string.share_new_title),
                    style = KafkaTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = KafkaTheme.materialColors.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
                ListItem(
                    headlineContent = {
                        Text(
                            stringResource(Res.string.share_new_activity),
                        )
                    },
                    leadingContent = {
                        Icon(
                            KafkaIcons.EventListOutline,
                            contentDescription = null,
                        )
                    },
                    onClick = {},
                )
                ListItem(
                    headlineContent = {
                        Text(
                            stringResource(Res.string.share_new_switch_log),
                        )
                    },
                    leadingContent = {
                        Icon(
                            KafkaIcons.SwitchAccountOutline,
                            contentDescription = null,
                        )
                    },
                    onClick = {},
                )
                ListItem(
                    headlineContent = {
                        Text(
                            stringResource(Res.string.share_new_quick_note),
                        )
                    },
                    leadingContent = {
                        Icon(
                            KafkaIcons.NoteStackOutline,
                            contentDescription = null,
                        )
                    },
                    onClick = {},
                )
                Text(
                    stringResource(Res.string.share_send_to_chat),
                    style = KafkaTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = KafkaTheme.materialColors.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
        }
    }
}

@Composable
@HanekokoroInject.ContributesRenderer(SystemScope::class)
internal fun ShareUI(
    component: ShareComponent,
    modifier: Modifier = Modifier,
) {
    ShareUI(
        modifier = modifier,
        shareData = component.params.shareData,
        onBack = component::navigateUp,
    )
}
