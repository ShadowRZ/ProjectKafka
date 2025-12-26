package io.github.shadowrz.projectkafka.features.preferences.impl.root

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import io.github.shadowrz.projectkafka.features.preferences.impl.R
import io.github.shadowrz.projectkafka.libraries.components.preferences.SwitchPreference
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ArrowBack
import io.github.shadowrz.projectkafka.libraries.icons.material.DatabaseOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.GroupsOutline
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@OptIn(ExperimentalMaterial3Api::class)
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
                colors =
                    topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {
                    Text(
                        stringResource(CommonStrings.common_settings),
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
        Column(modifier = Modifier.padding(innerPadding)) {
            SwitchPreference(
                checked = state.allowsMultiSystem,
                onCheckedChange = { state.eventSink(PreferencesRootEvents.ChangeAllowsMultiSystem(it)) },
                headlineContent = {
                    Text(
                        stringResource(R.string.preferences_multi_system),
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(R.string.preferences_multi_system_description),
                    )
                },
                leadingContent = {
                    Icon(
                        MaterialIcons.GroupsOutline,
                        contentDescription = null,
                    )
                },
            )
            ListItem(
                modifier = Modifier.clickable(onClick = onDataManage),
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                headlineContent = {
                    Text(
                        stringResource(CommonStrings.common_data_management),
                    )
                },
                leadingContent = {
                    Icon(
                        MaterialIcons.DatabaseOutline,
                        contentDescription = null,
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(R.string.preferences_datamanage_description),
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
