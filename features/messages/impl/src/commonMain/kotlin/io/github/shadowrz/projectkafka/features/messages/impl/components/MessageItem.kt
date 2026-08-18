package io.github.shadowrz.projectkafka.features.messages.impl.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.KafkaShapes
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MessageID
import io.github.shadowrz.projectkafka.libraries.kafkaui.MemberName
import io.github.shadowrz.projectkafka.libraries.richeditor.RichText
import io.github.shadowrz.projectkafka.libraries.richeditor.RichTextState
import kotlin.time.Instant
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalRichTextApi::class)
@Composable
internal fun MessageItem(
    message: ChatMessage,
    modifier: Modifier = Modifier,
    showAvatar: Boolean = true,
    showName: Boolean = true,
    isMe: Boolean = false,
) {
    fun LayoutDirection.reversed() =
        when (this) {
            LayoutDirection.Ltr -> LayoutDirection.Rtl
            LayoutDirection.Rtl -> LayoutDirection.Ltr
        }

    val currentLayoutDirection = LocalLayoutDirection.current
    val layoutDirection = if (isMe) currentLayoutDirection.reversed() else currentLayoutDirection
    val color = if (isMe) KafkaTheme.colors.primaryContainer else KafkaTheme.colors.tertiaryContainer

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Avatar(
                avatar = message.member.avatar?.value,
                modifier = Modifier.size(40.dp).visible(showAvatar),
            )
            Column {
                if (showName) {
                    MemberName(
                        message.member,
                        style = KafkaTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                val content = RichTextState(html = message.content)

                RichText(
                    content,
                    modifier = Modifier.clip(KafkaShapes.Medium).background(color = color).padding(10.dp),
                    color = Color.Black,
                )
            }
        }
    }
}

@KafkaPreview
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
        MessageItem(
            ChatMessage(
                id = MessageID(3),
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
            showName = true,
            isMe = true,
        )
    }
}
