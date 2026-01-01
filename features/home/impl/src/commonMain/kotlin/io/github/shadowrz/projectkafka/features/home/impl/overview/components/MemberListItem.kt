package io.github.shadowrz.projectkafka.features.home.impl.overview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.components.Avatar
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import kotlinx.datetime.LocalDate

@Composable
internal fun MemberListItem(
    member: Member,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    ListItem(
        modifier =
            modifier.clickable(
                onClick = onClick,
            ),
        headlineContent = {
            Text(
                text = member.name,
            )
        },
        supportingContent = {
            if (!member.description.isNullOrEmpty()) {
                Text(
                    text = member.description!!,
                    maxLines = 1,
                )
            }
        },
        leadingContent = {
            Avatar(
                avatar = member.avatar,
                modifier = Modifier.size(40.dp),
            )
        },
    )
}

@Preview
@Composable
private fun PreviewMemberListItem() {
    ProjectKafkaPreview {
        MemberListItem(
            member =
                Member(
                    id = MemberID("1"),
                    name = "N",
                    description = "Hello world",
                    avatar = null,
                    cover = null,
                    preferences = "",
                    roles = "",
                    birth = LocalDate(2024, 1, 1),
                    admin = false,
                ),
        )
    }
}
