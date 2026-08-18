package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.rememberAsyncImagePainter
import com.composeunstyled.UnstyledAvatar
import io.github.shadowrz.projectkafka.designsystem.internal.EmptyAvatar
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    avatar: String? = null,
    contentDescription: String? = null,
    hideAvatarImage: Boolean = false,
) {
    val painter = if (hideAvatarImage) null else avatar?.let { rememberAsyncImagePainter(it) }

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
@KafkaPreview
internal fun PreviewAvatar() = KafkaPreview {
    Avatar()
}
