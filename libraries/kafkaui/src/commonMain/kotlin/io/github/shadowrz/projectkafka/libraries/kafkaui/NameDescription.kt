package io.github.shadowrz.projectkafka.libraries.kafkaui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.ShieldOutline
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import org.jetbrains.compose.resources.stringResource
import projectkafka.libraries.kafkaui.generated.resources.Res
import projectkafka.libraries.kafkaui.generated.resources.unknown_chat_name

private const val ADMIN_ID = "adminIcon"

@Composable
fun MemberName(
    member: Member,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    fontWeight: FontWeight? = null,
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

@Composable
fun MemberDescription(
    member: Member,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    fontWeight: FontWeight? = null,
    singleLine: Boolean = false,
    placeholder: String? = null,
) {
    if (!member.description.isNullOrEmpty()) {
        Text(
            text = member.description!!,
            modifier = modifier,
            maxLines = 1,
            color = color,
            style = style,
            fontWeight = fontWeight,
            singleLine = singleLine,
        )
    } else if (placeholder != null) {
        Text(
            text = placeholder,
            modifier = modifier,
            maxLines = 1,
            color = color.copy(alpha = 0.5f),
            style = style,
            fontWeight = fontWeight,
            singleLine = singleLine,
            fontStyle = FontStyle.Italic,
        )
    }
}

@Composable
fun ChatName(
    chat: Chat,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    fontWeight: FontWeight? = null,
) {
    Text(
        chat.name ?: stringResource(Res.string.unknown_chat_name),
        modifier = modifier,
        color = color,
        style = style,
        fontWeight = fontWeight,
        fontStyle = if (chat.name.isNullOrBlank()) FontStyle.Italic else null,
    )
}
