package io.github.shadowrz.projectkafka.features.home.impl.overview.members

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
import io.github.shadowrz.projectkafka.features.home.impl.overview.components.MemberListItem
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_new_member
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.dashboard_no_members

@Composable
internal fun MembersUI(
    state: MembersState,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    onNewMember: () -> Unit = {},
    onMemberClick: (MemberID) -> Unit = {},
) {
    when (state.members) {
        Result.Loading -> {
            Box(modifier = modifier.fillMaxSize()) {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }

        is Result.Success<List<Member>> -> {
            if (state.members.value.isEmpty()) {
                EmptyContent(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                    onNewMember = onNewMember,
                )
            } else {
                LazyColumn(
                    state = lazyListState,
                    modifier = modifier.fillMaxSize(),
                ) {
                    items(
                        items = state.members.value,
                        key = { it.id.value },
                    ) { item ->
                        MemberListItem(
                            member = item,
                            onClick = { onMemberClick(item.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyContent(
    modifier: Modifier = Modifier,
    onNewMember: () -> Unit = {},
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
            stringResource(Res.string.dashboard_no_members),
            color = KafkaTheme.materialColors.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        TextButton(
            text = stringResource(CommonStrings.common_new_member),
            leadingIcon = KafkaIcons.Add,
            onClick = onNewMember,
        )
    }
}
