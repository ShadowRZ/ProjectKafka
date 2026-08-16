package io.github.shadowrz.projectkafka.features.messages.impl.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import io.github.shadowrz.projectkafka.designsystem.KafkaShapes
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MessageID
import io.github.shadowrz.projectkafka.libraries.richeditor.RichText
import io.github.shadowrz.projectkafka.libraries.richeditor.RichTextState
import kotlin.time.Instant
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalRichTextApi::class)
@Composable
internal fun NarratorItem(
    message: ChatMessage,
    modifier: Modifier = Modifier,
) {
    val content = RichTextState(html = message.content)

    DisposableEffect(message.content) {
        content.setHtml(message.content)
        onDispose {}
    }

    RichText(
        content,
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .clip(KafkaShapes.Small)
                .background(color = KafkaTheme.colors.inverseOnSurface)
                .padding(6.dp),
        color = KafkaTheme.colors.onSurface,
    )
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewNarratorItem() = KafkaPreview {
    Column(
        modifier = Modifier.width(400.dp).padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        NarratorItem(
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
            )
        )
        NarratorItem(
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
            )
        )
    }
}
