package io.github.shadowrz.projectkafka.features.home.impl.overview.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.SwitchAccountOutline
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_fronterindicator
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.fronterindicator_description

@Composable
internal fun ToolsUI(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    onFronterIndicator: () -> Unit = {},
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            ListItem(
                onClick = onFronterIndicator,
                headlineContent = {
                    Text(
                        stringResource(CommonStrings.common_fronterindicator),
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(Res.string.fronterindicator_description),
                    )
                },
                leadingContent = {
                    Box(
                        modifier = Modifier.size(40.dp).clip(CircleShape).background(KafkaTheme.materialColors.surfaceVariant),
                    ) {
                        Icon(
                            KafkaIcons.SwitchAccountOutline,
                            modifier = Modifier.align(Alignment.Center),
                            contentDescription = null,
                            tint = KafkaTheme.materialColors.onSurfaceVariant,
                        )
                    }
                },
            )
        }
    }
}
