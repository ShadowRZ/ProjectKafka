package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ArrowBack
import io.github.shadowrz.projectkafka.libraries.icons.material.BackupOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.SettingsBackupRestore
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@SingleIn(AppScope::class)
@Inject
@ContributesIntoMap(
    AppScope::class,
    binding = binding<ComponentUI<*>>(),
)
@ComponentKey(DataManageComponent::class)
class DataManageUI : ComponentUI<DataManageComponent> {
    @Composable
    override fun Content(
        component: DataManageComponent,
        modifier: Modifier,
    ) {
        val state = component.presenter.present()

        DataManageUI(
            state = state,
            onBack = component::onBack,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DataManageUI(
    state: DataManageState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
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
                        stringResource(CommonStrings.common_data_management),
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
        snackbarHost = {
            SnackbarHost(hostState = state.snackbarHostState)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ListItem(
                modifier = Modifier.clickable(onClick = {
                    state.eventSink(DataManageEvents.Backup)
                }),
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                headlineContent = {
                    Text(
                        stringResource(R.string.datamanage_backup),
                    )
                },
                leadingContent = {
                    Icon(
                        MaterialIcons.BackupOutline,
                        contentDescription = null,
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(R.string.datamanage_backup_description),
                    )
                },
            )
            ListItem(
                modifier = Modifier.clickable(onClick = {
                    state.eventSink(DataManageEvents.Restore)
                }),
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                headlineContent = {
                    Text(
                        stringResource(R.string.datamanage_restore),
                    )
                },
                leadingContent = {
                    Icon(
                        MaterialIcons.SettingsBackupRestore,
                        contentDescription = null,
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(R.string.datamanage_restore_description),
                    )
                },
            )
        }
    }
}
