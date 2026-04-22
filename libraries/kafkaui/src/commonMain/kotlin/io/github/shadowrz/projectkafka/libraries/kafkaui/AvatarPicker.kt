package io.github.shadowrz.projectkafka.libraries.kafkaui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.OutlinedIconButton
import io.github.shadowrz.projectkafka.designsystem.icons.AccountCircleOutline
import io.github.shadowrz.projectkafka.designsystem.icons.EditOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import kotlinx.serialization.Serializable

@Composable
fun AvatarPicker(
    state: AvatarPickerState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    size: Dp = 128.dp,
    icon: ImageVector = KafkaIcons.AccountCircleOutline,
) {
    Box(
        modifier = modifier.graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        },
    ) {
        when (state) {
            is AvatarPickerState.Pick -> {
                OutlinedIconButton(
                    modifier = Modifier.size(size),
                    onClick = onClick,
                ) {
                    Icon(
                        icon,
                        modifier = Modifier.size(size * 0.75f),
                        contentDescription = null,
                    )
                }
            }

            is AvatarPickerState.Selected -> {
                Avatar(
                    avatar = state.value,
                    modifier = Modifier.size(size).clip(CircleShape).clickable(onClick = onClick),
                )
                OutlinedIconButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .drawWithContent {
                            drawCircle(
                                color = Color.Black,
                                radius = this.size.maxDimension / 2f,
                                blendMode = BlendMode.Clear,
                            )
                            drawContent()
                        },
                    onClick = onClick,
                ) {
                    Icon(
                        KafkaIcons.EditOutline,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Immutable
@Serializable
sealed interface AvatarPickerState {
    @Serializable
    data object Pick : AvatarPickerState

    @Serializable
    data class Selected(
        val value: Uri,
    ) : AvatarPickerState
}

@Composable
@PreviewLightDark
internal fun PreviewAvatarPicker() =
    KafkaPreview {
        Column {
            AvatarPicker(AvatarPickerState.Pick)
            AvatarPicker(AvatarPickerState.Selected(value = Uri.EMPTY))
        }
    }
