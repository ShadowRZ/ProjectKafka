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
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.designsystem.internal.EmptyAvatar
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    avatar: Uri? = null,
    contentDescription: String? = null,
) {
    SubcomposeAsyncImage(
        avatar?.toString(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.aspectRatio(1f).clip(CircleShape),
    ) {
        val collectedState by painter.state.collectAsState()
        when (val state = collectedState) {
            AsyncImagePainter.State.Empty -> {
                EmptyAvatar()
            }

            is AsyncImagePainter.State.Error -> {
                SideEffect {
                    Logger.e("Error loading avatar $state", state.result.throwable)
                }
                EmptyAvatar()
            }

            is AsyncImagePainter.State.Loading -> {}

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
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
