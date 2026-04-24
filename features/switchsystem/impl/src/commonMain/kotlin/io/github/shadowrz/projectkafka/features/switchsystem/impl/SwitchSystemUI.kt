package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.IconButton
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.icons.Add
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_new_system
import io.github.shadowrz.projectkafka.libraries.strings.common_switch_system
import org.jetbrains.compose.resources.stringResource

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
                titleStr = stringResource(CommonStrings.common_switch_system),
                navigationIcon = {
                    BackButton(onClick = onBack)
                },
                actions = {
                    IconButton(onClick = { state.eventSink(SwitchSystemEvents.CreateSystem) }) {
                        Icon(
                            imageVector = KafkaIcons.Add,
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
                            headlineContent = {
                                Text(it.name)
                            },
                            leadingContent = {
                                Avatar(
                                    avatar = it.avatar?.value,
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
