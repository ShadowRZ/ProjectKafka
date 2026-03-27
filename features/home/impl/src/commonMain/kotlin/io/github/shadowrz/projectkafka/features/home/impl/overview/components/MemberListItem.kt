package io.github.shadowrz.projectkafka.features.home.impl.overview.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
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
        modifier = modifier,
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
        onClick = onClick,
    )
}

@Composable
@PreviewLightDark
internal fun PreviewMemberListItem() =
    KafkaPreview {
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
