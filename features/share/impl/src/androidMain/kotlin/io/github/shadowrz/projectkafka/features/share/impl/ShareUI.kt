package io.github.shadowrz.projectkafka.features.share.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.features.share.api.ShareData
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ArrowBack
import io.github.shadowrz.projectkafka.libraries.icons.material.EventListOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.NoteStackOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.SwitchAccountOutline
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_back
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.share.impl.generated.resources.Res
import projectkafka.features.share.impl.generated.resources.share_new_activity
import projectkafka.features.share.impl.generated.resources.share_new_quick_note
import projectkafka.features.share.impl.generated.resources.share_new_switch_log
import projectkafka.features.share.impl.generated.resources.share_new_title
import projectkafka.features.share.impl.generated.resources.share_send_to_chat
import projectkafka.features.share.impl.generated.resources.share_title

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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
                colors =
                    topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {
                    Text(
                        stringResource(Res.string.share_title),
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
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
        ) {
            item {
                Text(
                    stringResource(Res.string.share_new_title),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
                ListItem(
                    modifier = Modifier.clickable {},
                    headlineContent = {
                        Text(
                            stringResource(Res.string.share_new_activity),
                        )
                    },
                    leadingContent = {
                        Icon(
                            MaterialIcons.EventListOutline,
                            contentDescription = null,
                        )
                    },
                )
                ListItem(
                    modifier = Modifier.clickable {},
                    headlineContent = {
                        Text(
                            stringResource(Res.string.share_new_switch_log),
                        )
                    },
                    leadingContent = {
                        Icon(
                            MaterialIcons.SwitchAccountOutline,
                            contentDescription = null,
                        )
                    },
                )
                ListItem(
                    modifier = Modifier.clickable {},
                    headlineContent = {
                        Text(
                            stringResource(Res.string.share_new_quick_note),
                        )
                    },
                    leadingContent = {
                        Icon(
                            MaterialIcons.NoteStackOutline,
                            contentDescription = null,
                        )
                    },
                )
                Text(
                    stringResource(Res.string.share_send_to_chat),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
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
