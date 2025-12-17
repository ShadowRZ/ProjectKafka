package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LoadingIndicator
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
import androidx.compose.ui.unit.dp
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.libraries.components.Avatar
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Add
import io.github.shadowrz.projectkafka.libraries.icons.material.ArrowBack
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun SwitchSystemUI(
    component: SwitchSystemComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    SwitchSystemUI(
        state = state,
        modifier = modifier,
        onBack = component::navigateUp,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
private fun SwitchSystemUI(
    state: SwitchSystemState,
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
                        stringResource(CommonStrings.common_switch_system),
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
                actions = {
                    IconButton(onClick = { state.eventSink(SwitchSystemEvents.CreateSystem) }) {
                        Icon(
                            imageVector = MaterialIcons.Add,
                            contentDescription = stringResource(CommonStrings.common_new_system),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        when (state.systems) {
            Result.Loading -> {
                LoadingIndicator(modifier = Modifier.padding(innerPadding).fillMaxSize().wrapContentSize())
            }

            is Result.Success<List<System>> -> {
                LazyColumn(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    items(state.systems.value, key = { it.id.value }) {
                        ListItem(
                            modifier = Modifier.animateItem().clickable { state.eventSink(SwitchSystemEvents.SwitchSystem(it.id)) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            headlineContent = {
                                Text(it.name)
                            },
                            leadingContent = {
                                Avatar(
                                    avatar = it.avatar,
                                    modifier = Modifier.size(40.dp),
                                )
                            },
                            supportingContent = {
                                if (!it.description.isNullOrEmpty()) {
                                    Text(
                                        text = it.description!!,
                                        maxLines = 1,
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}
