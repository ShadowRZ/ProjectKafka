package io.github.shadowrz.projectkafka.features.home.impl.overview.members

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.features.home.impl.R
import io.github.shadowrz.projectkafka.features.home.impl.overview.components.MemberListItem
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Add
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun MembersUI(
    state: MembersState,
    nestedScrollConnection: NestedScrollConnection,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    onNewMember: () -> Unit = {},
    onMemberClick: (MemberID) -> Unit = {},
) {
    when (state.members) {
        Result.Loading -> {
            Box(modifier = modifier.fillMaxSize()) {
                CircularWavyProgressIndicator(
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
                    modifier =
                        modifier.fillMaxSize().nestedScroll(nestedScrollConnection),
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
            stringResource(R.string.dashboard_no_members),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        TextButton(
            onClick = { onNewMember() },
        ) {
            Icon(
                MaterialIcons.Add,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text(
                stringResource(CommonStrings.common_new_member),
            )
        }
    }
}
