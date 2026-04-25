package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import co.touchlab.kermit.Logger
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import io.github.shadowrz.projectkafka.designsystem.internal.EmptyAvatar
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    avatar: String? = null,
    contentDescription: String? = null,
    hideAvatarImage: Boolean = false,
) {
    when {
        avatar.isNullOrBlank() || hideAvatarImage -> EmptyAvatar(modifier = modifier)

        else -> ImageAvatar(
            modifier = modifier,
            avatar = avatar,
            contentDescription = contentDescription,
        )
    }
}

@Composable
private fun ImageAvatar(
    avatar: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    SubcomposeAsyncImage(
        avatar,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.aspectRatio(1f).clip(CircleShape),
    ) {
        val collectedState by painter.state.collectAsState()
        when (val state = collectedState) {
            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }

            is AsyncImagePainter.State.Error -> {
                SideEffect {
                    Logger.e("Error loading avatar ${state.result.request.data}", state.result.throwable)
                }
                EmptyAvatar()
            }

            else -> {
                EmptyAvatar()
            }
        }
    }
}

@Composable
@PreviewLightDark
internal fun PreviewAvatar() =
    KafkaPreview {
        Avatar()
    }
