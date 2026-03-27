package io.github.shadowrz.projectkafka.features.home.impl.timeline.frontlog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TextButton
import io.github.shadowrz.projectkafka.designsystem.icons.Add
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.FrontLog
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.timeline_new_front_log
import projectkafka.features.home.impl.generated.resources.timeline_no_front_logs

@Composable
internal fun FrontLogUI(
    state: FrontLogsState,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    when (state.frontLogs) {
        Result.Loading -> {
            Box(modifier = modifier.fillMaxSize()) {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }

        is Result.Success<List<FrontLog>> -> {
            if (state.frontLogs.value.isEmpty()) {
                EmptyContent(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                )
            } else {
                LazyColumn(
                    state = lazyListState,
                    modifier = modifier.fillMaxSize(),
                ) {
                    items(
                        items = state.frontLogs.value,
                        key = { it.id.value },
                    ) { item ->
                        Text(item.id.value)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyContent(
    modifier: Modifier = Modifier,
    onNewFrontLog: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(Res.string.timeline_no_front_logs),
            color = KafkaTheme.materialColors.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        TextButton(
            text = stringResource(Res.string.timeline_new_front_log),
            leadingIcon = KafkaIcons.Add,
            onClick = { onNewFrontLog() },
        )
    }
}
