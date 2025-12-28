package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import coil3.compose.AsyncImage
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.components.preview.PreviewGroups
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.core.extensions.isNullOrEmpty
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.AccountCircle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    avatar: Uri? = null,
    contentDescription: String? = null,
    fallback: @Composable () -> Unit = { IconFallback() },
) {
    Box(
        modifier = modifier.aspectRatio(1f).clip(CircleShape),
    ) {
        if (avatar.isNullOrEmpty()) {
            IconFallback()
            Box(
                modifier =
                    Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            ) {
                fallback()
            }
        } else {
            AsyncImage(
                model = avatar.toString(),
                contentDescription = contentDescription,
            )
        }
    }
}

@Composable
internal fun IconFallback(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.fillMaxSize().scale(0.75f),
        imageVector = MaterialIcons.AccountCircle,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Preview(name = "Avatar", group = PreviewGroups.Avatar)
@Composable
internal fun PreviewAvatar() =
    ProjectKafkaPreview {
        Avatar()
    }
