package io.github.shadowrz.projectkafka.features.preferences.impl.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.icons.DatabaseOutline
import io.github.shadowrz.projectkafka.designsystem.icons.EditOutline
import io.github.shadowrz.projectkafka.designsystem.icons.GroupsOutline
import io.github.shadowrz.projectkafka.designsystem.preferences.SwitchPreference
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_data_management
import io.github.shadowrz.projectkafka.libraries.strings.common_settings
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.preferences.impl.generated.resources.Res
import projectkafka.features.preferences.impl.generated.resources.preferences_datamanage_description
import projectkafka.features.preferences.impl.generated.resources.preferences_multi_system
import projectkafka.features.preferences.impl.generated.resources.preferences_multi_system_description
import projectkafka.features.preferences.impl.generated.resources.preferences_use_system_font
import projectkafka.features.preferences.impl.generated.resources.preferences_use_system_font_description

@Composable
internal fun PreferencesRootUI(
    state: PreferencesRootState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onDataManage: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(CommonStrings.common_settings),
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    BackButton(onClick = onBack)
                },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SwitchPreference(
                checked = state.allowsMultiSystem,
                onCheckedChange = { state.eventSink(PreferencesRootEvents.ChangeAllowsMultiSystem(it)) },
                headlineContent = {
                    Text(
                        stringResource(Res.string.preferences_multi_system),
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(Res.string.preferences_multi_system_description),
                    )
                },
                leadingContent = {
                    Icon(
                        KafkaIcons.GroupsOutline,
                        contentDescription = null,
                    )
                },
            )
            SwitchPreference(
                checked = state.useSystemFont,
                onCheckedChange = { state.eventSink(PreferencesRootEvents.ChangeUseSystemFont(it)) },
                headlineContent = {
                    Text(
                        stringResource(Res.string.preferences_use_system_font),
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(Res.string.preferences_use_system_font_description),
                    )
                },
                leadingContent = {
                    Icon(
                        KafkaIcons.EditOutline,
                        contentDescription = null,
                    )
                },
            )
            ListItem(
                onClick = onDataManage,
                headlineContent = {
                    Text(
                        stringResource(CommonStrings.common_data_management),
                    )
                },
                leadingContent = {
                    Icon(
                        KafkaIcons.DatabaseOutline,
                        contentDescription = null,
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(Res.string.preferences_datamanage_description),
                    )
                },
            )
        }
    }
}

@Composable
internal fun PreferencesRootUI(
    component: PreferencesRootComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    PreferencesRootUI(
        state = state,
        modifier = modifier,
        onBack = component::navigateUp,
        onDataManage = component::onDataManage,
    )
}
