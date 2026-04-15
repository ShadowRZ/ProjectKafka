package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.SnackbarHost
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.icons.BackupOutline
import io.github.shadowrz.projectkafka.designsystem.icons.SettingsBackupRestore
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_data_management
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.datamanage.impl.generated.resources.Res
import projectkafka.features.datamanage.impl.generated.resources.datamanage_backup
import projectkafka.features.datamanage.impl.generated.resources.datamanage_backup_description
import projectkafka.features.datamanage.impl.generated.resources.datamanage_restore
import projectkafka.features.datamanage.impl.generated.resources.datamanage_restore_description

@HanekokoroInject.ContributesRenderer(AppScope::class)
@Composable
internal fun DataManageUI(
    component: DataManageComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    DataManageUI(
        state = state,
        onBack = component::navigateUp,
        modifier = modifier,
    )
}

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
                titleStr = stringResource(CommonStrings.common_data_management),
                navigationIcon = {
                    BackButton(onClick = onBack)
                },
            )
        },
        snackbarHost = {
            SnackbarHost()
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ListItem(
                onClick = {
                    state.eventSink(DataManageEvents.Backup)
                },
                headlineContent = {
                    Text(
                        stringResource(Res.string.datamanage_backup),
                    )
                },
                leadingContent = {
                    Icon(
                        KafkaIcons.BackupOutline,
                        contentDescription = null,
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(Res.string.datamanage_backup_description),
                    )
                },
            )
            ListItem(
                onClick = {
                    state.eventSink(DataManageEvents.Restore)
                },
                headlineContent = {
                    Text(
                        stringResource(Res.string.datamanage_restore),
                    )
                },
                leadingContent = {
                    Icon(
                        KafkaIcons.SettingsBackupRestore,
                        contentDescription = null,
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(Res.string.datamanage_restore_description),
                    )
                },
            )
        }
    }
}
