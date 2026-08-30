package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import co.touchlab.kermit.Logger
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
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
@PreviewKafka
internal fun PreviewOutlinedAvatar() = KafkaPreview {
    OutlinedAvatar()
}
