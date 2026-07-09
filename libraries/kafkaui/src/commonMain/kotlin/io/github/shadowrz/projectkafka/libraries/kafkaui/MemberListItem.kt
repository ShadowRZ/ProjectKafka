package io.github.shadowrz.projectkafka.libraries.kafkaui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import kotlinx.datetime.LocalDate

@Composable
fun MemberListItem(
    member: Member,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            MemberName(member = member)
        },
        supportingContent = {
            MemberDescription(member = member, singleLine = true)
        },
        leadingContent = {
            Avatar(
                avatar = member.avatar?.value,
                modifier = Modifier.size(40.dp),
            )
        },
        trailingContent = trailingContent,
        onClick = onClick,
    )
}

@Composable
@PreviewLightDark
internal fun PreviewMemberListItem() = KafkaPreview {
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
            )
    )
}
