package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun AvatarGroup(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    offset: Dp = (-20).dp,
    borderColor: Color = Color.Black,
    borderStrokeWidth: Float = 4f,
    borderBlendMode: BlendMode = BlendMode.Clear,
    content: @Composable AvatarGroupScope.() -> Unit = {},
) {
    Row(
        modifier = modifier.graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        },
        horizontalArrangement = Arrangement.spacedBy(offset),
    ) {
        val scope =
            remember {
                AvatarGroupScopeImpl(
                    scope = this,
                    size = size,
                    borderColor = borderColor,
                    borderStrokeWidth = borderStrokeWidth,
                    borderBlendMode = borderBlendMode,
                )
            }

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
) {
    io.github.shadowrz.projectkafka.designsystem.Avatar(
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
                },
        avatar = avatar,
        contentDescription = contentDescription,
    )
}

@Composable
fun AvatarGroupScope.TextFallback(
    text: String,
    modifier: Modifier = Modifier,
) {
    io.github.shadowrz.projectkafka.designsystem.internal.TextAvatar(
        text = text,
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
                },
    )
}

internal class AvatarGroupScopeImpl(
    val scope: RowScope,
    override val size: Dp,
    override val borderColor: Color,
    override val borderStrokeWidth: Float,
    override val borderBlendMode: BlendMode,
) : AvatarGroupScope,
    RowScope by scope

@Composable
@PreviewLightDark
internal fun PreviewAvatarGroup() =
    KafkaPreview {
        AvatarGroup(
            modifier = Modifier.padding(4.dp),
        ) {
            Avatar()
            Avatar()
            Avatar()
            Avatar()
            TextFallback("+4")
        }
    }
