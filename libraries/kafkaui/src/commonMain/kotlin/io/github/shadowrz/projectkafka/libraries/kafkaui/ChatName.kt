package io.github.shadowrz.projectkafka.libraries.kafkaui

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import org.jetbrains.compose.resources.stringResource
import projectkafka.libraries.kafkaui.generated.resources.Res
import projectkafka.libraries.kafkaui.generated.resources.unknown_chat_name

@Composable
fun ChatName(
    chat: Chat,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    fontWeight: FontWeight? = FontWeight.Bold,
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
