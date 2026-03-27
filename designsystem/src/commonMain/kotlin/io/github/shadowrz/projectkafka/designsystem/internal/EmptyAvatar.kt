package io.github.shadowrz.projectkafka.designsystem.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.icons.AccountCircle
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
internal fun EmptyAvatar(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(KafkaTheme.materialColors.primaryContainer)
            .scale(0.75f),
        imageVector = KafkaIcons.AccountCircle,
        contentDescription = null,
        tint = KafkaTheme.materialColors.onPrimaryContainer,
    )
}

@Composable
@PreviewLightDark
internal fun PreviewEmptyAvatar() =
    KafkaPreview {
        EmptyAvatar()
    }
