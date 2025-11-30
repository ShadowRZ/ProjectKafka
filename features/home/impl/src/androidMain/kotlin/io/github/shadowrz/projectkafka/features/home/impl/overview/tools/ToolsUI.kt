package io.github.shadowrz.projectkafka.features.home.impl.overview.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.features.home.impl.R
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.SwitchAccountOutline
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

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
                modifier = Modifier.clickable { onFronterIndicator() },
                headlineContent = {
                    Text(
                        stringResource(CommonStrings.common_fronterindicator),
                    )
                },
                supportingContent = {
                    Text(
                        stringResource(R.string.fronterindicator_description),
                    )
                },
                leadingContent = {
                    Box(
                        modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant),
                    ) {
                        Icon(
                            MaterialIcons.SwitchAccountOutline,
                            modifier = Modifier.align(Alignment.Center),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
            )
        }
    }
}
