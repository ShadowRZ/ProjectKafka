package io.github.shadowrz.projectkafka.libraries.kafkaui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.ListItemStyle
import io.github.shadowrz.projectkafka.designsystem.ModalBottomSheet
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.CameraOutline
import io.github.shadowrz.projectkafka.designsystem.icons.DeleteOutline
import io.github.shadowrz.projectkafka.designsystem.icons.ImageOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import org.jetbrains.compose.resources.stringResource
import projectkafka.libraries.kafkaui.generated.resources.Res
import projectkafka.libraries.kafkaui.generated.resources.profile_capture_from_camera
import projectkafka.libraries.kafkaui.generated.resources.profile_clear_image
import projectkafka.libraries.kafkaui.generated.resources.profile_select_from_gallery

@Composable
fun MediaPickerBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    onCamera: () -> Unit,
    onClear: () -> Unit,
    onGallery: () -> Unit,
    modifier: Modifier = Modifier,
    showCamera: Boolean = true,
) {
    if (visible) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
        ) {
            Column {
                if (showCamera) {
                    ListItem(
                        onClick = onCamera,
                        headlineContent = {
                            Text(
                                stringResource(Res.string.profile_capture_from_camera),
                            )
                        },
                        leadingContent = {
                            Icon(
                                KafkaIcons.CameraOutline,
                                contentDescription = null,
                            )
                        },
                    )
                }
                ListItem(
                    onClick = onGallery,
                    headlineContent = {
                        Text(
                            stringResource(Res.string.profile_select_from_gallery),
                        )
                    },
                    leadingContent = {
                        Icon(
                            KafkaIcons.ImageOutline,
                            contentDescription = null,
                        )
                    },
                )
                ListItem(
                    onClick = onClear,
                    headlineContent = {
                        Text(
                            stringResource(Res.string.profile_clear_image),
                        )
                    },
                    leadingContent = {
                        Icon(
                            KafkaIcons.DeleteOutline,
                            contentDescription = null,
                        )
                    },
                    style = ListItemStyle.Destructive,
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
internal fun PreviewMediaPickerBottomSheet() =
    KafkaPreview {
        Box(modifier = Modifier.fillMaxSize()) {
            MediaPickerBottomSheet(
                visible = true,
                onDismiss = {},
                onCamera = {},
                onClear = {},
                onGallery = {},
            )
        }
    }
