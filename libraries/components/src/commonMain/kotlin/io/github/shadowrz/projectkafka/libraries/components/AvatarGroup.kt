package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.components.preview.PreviewGroups
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AvatarGroup(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    offset: Dp = (-20).dp,
    borderColor: Color = Color.White,
    borderStrokeWidth: Float = 4f,
    borderBlendMode: BlendMode = BlendMode.Src,
    content: @Composable AvatarGroupScope.() -> Unit = {},
) {
    val scope =
        remember {
            AvatarGroupScopeImpl(
                size = size,
                borderColor = borderColor,
                borderStrokeWidth = borderStrokeWidth,
                borderBlendMode = borderBlendMode,
            )
        }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(offset),
    ) {
        scope.content()
    }
}

interface AvatarGroupScope {
    val size: Dp
    val borderColor: Color
    val borderStrokeWidth: Float
    val borderBlendMode: BlendMode
}

@Composable
fun AvatarGroupScope.Avatar(
    modifier: Modifier = Modifier,
    avatar: Uri? = null,
    contentDescription: String? = null,
    fallback: @Composable () -> Unit = { IconFallback() },
) {
    io.github.shadowrz.projectkafka.libraries.components.Avatar(
        modifier =
            modifier
                .size(size)
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        color = borderColor,
                        style = Stroke(width = borderStrokeWidth),
                        radius = size.minDimension / 2,
                        blendMode = borderBlendMode,
                    )
                }.graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                },
        avatar = avatar,
        contentDescription = contentDescription,
        fallback = fallback,
    )
}

@Composable
fun AvatarGroupScope.Fallback(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .size(size)
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        color = borderColor,
                        style = Stroke(width = borderStrokeWidth),
                        radius = size.minDimension / 2,
                        blendMode = borderBlendMode,
                    )
                }.clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .wrapContentSize(),
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
            content()
        }
    }
}

internal class AvatarGroupScopeImpl(
    override val size: Dp,
    override val borderColor: Color,
    override val borderStrokeWidth: Float,
    override val borderBlendMode: BlendMode,
) : AvatarGroupScope

@Preview(name = "AvatarGroup", group = PreviewGroups.Avatar)
@Composable
internal fun PreviewAvatarGroup() =
    ProjectKafkaPreview {
        AvatarGroup(
            modifier = Modifier.padding(4.dp),
        ) {
            Avatar()
            Avatar()
            Avatar()
            Avatar()
            Fallback {
                Text(
                    "+4",
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
