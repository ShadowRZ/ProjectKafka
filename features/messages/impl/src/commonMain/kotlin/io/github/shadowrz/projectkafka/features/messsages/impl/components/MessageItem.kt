package io.github.shadowrz.projectkafka.features.messsages.impl.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MessageID
import io.github.shadowrz.projectkafka.libraries.kafkaui.MemberName
import kotlin.time.Instant
import kotlinx.datetime.LocalDate

@Composable
internal fun MessageItem(
    message: ChatMessage,
    modifier: Modifier = Modifier,
    showAvatar: Boolean = true,
    showName: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Avatar(modifier = Modifier.size(40.dp).visible(showAvatar))
        Column {
            if (showName) {
                MemberName(
                    message.member,
                    style = KafkaTheme.typography.titleMedium,
                )
            }
            Text(
                message.content,
                modifier =
                    Modifier.clip(RoundedCornerShape(16.dp)).background(color = KafkaTheme.materialColors.tertiaryContainer).padding(10.dp),
                color = Color.Black,
            )
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewMessageItem() = KafkaPreview {
    Column(
        modifier = Modifier.width(400.dp).padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        MessageItem(
            ChatMessage(
                id = MessageID(1),
                member =
                    Member(
                        id = MemberID("1"),
                        name = "N",
                        description = "",
                        avatar = null,
                        cover = null,
                        preferences = "",
                        roles = "",
                        birth = LocalDate(2024, 1, 1),
                        admin = false,
                    ),
                content = "Hello World",
                media = null,
                timestamp = Instant.fromEpochSeconds(1710630000),
            ),
            showAvatar = false,
        )
        MessageItem(
            ChatMessage(
                id = MessageID(2),
                member =
                    Member(
                        id = MemberID("1"),
                        name = "N",
                        description = "",
                        avatar = null,
                        cover = null,
                        preferences = "",
                        roles = "",
                        birth = LocalDate(2024, 1, 1),
                        admin = false,
                    ),
                content = "This is a test",
                media = null,
                timestamp = Instant.fromEpochSeconds(1710630000),
            ),
            showName = false,
        )
    }
}
