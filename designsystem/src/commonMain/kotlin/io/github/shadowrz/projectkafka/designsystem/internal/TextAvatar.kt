package io.github.shadowrz.projectkafka.designsystem.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
internal fun TextAvatar(
    text: String,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(KafkaTheme.materialColors.primaryContainer)
            .wrapContentSize(),
    ) {
        Text(text, color = KafkaTheme.materialColors.onPrimaryContainer)
    }
}

@Composable
@PreviewLightDark
internal fun PreviewTextAvatar() =
    KafkaPreview {
        TextAvatar("+1")
    }
