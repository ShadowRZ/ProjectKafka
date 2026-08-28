package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.rememberAsyncImagePainter
import com.composeunstyled.UnstyledAvatar
import io.github.shadowrz.projectkafka.designsystem.icons.AccountCircleOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.designsystem.preview.PreviewKafka

@Composable
fun OutlinedAvatar(
    modifier: Modifier = Modifier,
    avatar: String? = null,
    contentDescription: String? = null,
    hideAvatarImage: Boolean = false,
) {
    when {
        avatar.isNullOrBlank() || hideAvatarImage -> EmptyAvatar(modifier = modifier.fillMaxSize())

        else ->
            ImageAvatar(
                modifier = modifier,
                avatar = avatar,
                contentDescription = contentDescription,
            )
    }
}

@Composable
private fun EmptyAvatar(modifier: Modifier = Modifier) =
    Icon(
        modifier = modifier,
        imageVector = KafkaIcons.AccountCircleOutline,
        contentDescription = null,
        tint = KafkaTheme.colors.secondary,
    )

@Composable
private fun ImageAvatar(
    avatar: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val painter = rememberAsyncImagePainter(avatar)

    UnstyledAvatar(
        painter = painter,
        modifier = modifier.aspectRatio(1f).clip(CircleShape),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription,
        underlay = {
            EmptyAvatar()
        },
    )
}

@Composable
@PreviewKafka
internal fun PreviewOutlinedAvatar() = KafkaPreview {
    OutlinedAvatar()
}
