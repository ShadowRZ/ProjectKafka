package io.github.shadowrz.projectkafka.libraries.kafkaui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.ShieldOutline
import io.github.shadowrz.projectkafka.libraries.data.api.Member

private const val ADMIN_ID = "adminIcon"

@Composable
fun MemberName(
    member: Member,
    modifier: Modifier = Modifier,
    color: Color = KafkaTheme.materialColors.primary,
    style: TextStyle = KafkaTheme.typography.titleMedium,
    fontWeight: FontWeight? = FontWeight.Bold,
) {
    val annotatedText = buildAnnotatedString {
        append(member.name)
        if (member.admin) {
            appendInlineContent(ADMIN_ID, "[Admin]")
        }
    }

    Text(
        annotatedText,
        inlineContent =
            mapOf(
                Pair(
                    ADMIN_ID,
                    InlineTextContent(
                        placeholder =
                            Placeholder(
                                width = style.fontSize,
                                height = style.fontSize,
                                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                            )
                    ) {
                        Icon(
                            KafkaIcons.ShieldOutline,
                            modifier = Modifier.fillMaxSize().padding(2.dp),
                            contentDescription = null,
                        )
                    },
                )
            ),
        modifier = modifier,
        color = color,
        style = style,
        fontWeight = fontWeight,
    )
}
