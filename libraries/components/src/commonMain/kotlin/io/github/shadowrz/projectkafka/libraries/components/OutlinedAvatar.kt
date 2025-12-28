package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.components.preview.PreviewGroups
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.core.extensions.isNullOrEmpty
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.AccountCircleOutline
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OutlinedAvatar(
    modifier: Modifier = Modifier,
    avatar: Uri? = null,
    contentDescription: String? = null,
) {
    Crossfade(
        avatar,
        modifier = modifier.aspectRatio(1f).clip(CircleShape),
    ) { avatar ->
        if (avatar.isNullOrEmpty()) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = MaterialIcons.AccountCircleOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
            )
        } else {
            AsyncImage(
                model = avatar.toString(),
                contentDescription = contentDescription,
            )
        }
    }
}

@Preview(name = "OutlinedAvatar", group = PreviewGroups.Avatar)
@Composable
internal fun PreviewOutlinedAvatar() =
    ProjectKafkaPreview {
        OutlinedAvatar()
    }
