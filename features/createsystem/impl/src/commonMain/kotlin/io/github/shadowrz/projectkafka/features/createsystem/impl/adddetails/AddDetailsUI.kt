package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.attafitamim.krop.core.crop.AspectRatio
import com.attafitamim.krop.core.crop.cropperStyle
import com.attafitamim.krop.ui.ImageCropperDialog
import io.github.shadowrz.projectkafka.designsystem.Button
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.icons.ArrowForward
import io.github.shadowrz.projectkafka.designsystem.pages.FlowStepPage
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.kafkaui.AvatarPicker
import io.github.shadowrz.projectkafka.libraries.kafkaui.AvatarPickerState
import io.github.shadowrz.projectkafka.libraries.kafkaui.CoverPicker
import io.github.shadowrz.projectkafka.libraries.kafkaui.CoverPickerState
import io.github.shadowrz.projectkafka.libraries.kafkaui.MediaPickerBottomSheet
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_avatar
import io.github.shadowrz.projectkafka.libraries.strings.common_continue
import io.github.shadowrz.projectkafka.libraries.strings.common_cover
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.createsystem.impl.generated.resources.Res
import projectkafka.features.createsystem.impl.generated.resources.adddetails_description
import projectkafka.features.createsystem.impl.generated.resources.adddetails_title

@Composable
fun AddDetailsUI(
    state: AddDetailsState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    FlowStepPage(
        modifier = modifier,
        title = stringResource(Res.string.adddetails_title),
        subtitle = stringResource(Res.string.adddetails_description, state.systemName),
        scrollable = true,
        onBack = onBack,
        buttons = {
            Button(
                modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
                text = stringResource(CommonStrings.common_continue),
                onClick = { state.eventSink(AddDetailsEvents.CreateSystem) },
                enabled = !state.loading,
                progress = state.loading,
                leadingIcon = KafkaIcons.ArrowForward,
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(top = 24.dp).widthIn(max = 360.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    stringResource(CommonStrings.common_cover),
                    color = KafkaTheme.materialColors.primary,
                    fontWeight = FontWeight.Bold,
                )
                val coverState = remember(state.cover) {
                    if (state.avatar.isBlank()) {
                        CoverPickerState.Pick
                    } else {
                        CoverPickerState.Selected(state.cover)
                    }
                }
                CoverPicker(
                    state = coverState,
                    onClick = { state.eventSink(AddDetailsEvents.OpenCoverPickerSheet) },
                )
                Text(
                    stringResource(CommonStrings.common_avatar),
                    color = KafkaTheme.materialColors.primary,
                    fontWeight = FontWeight.Bold,
                )
                val avatarState = remember(state.avatar) {
                    if (state.avatar.isBlank()) {
                        AvatarPickerState.Pick
                    } else {
                        AvatarPickerState.Selected(state.avatar)
                    }
                }
                AvatarPicker(
                    state = avatarState,
                    onClick = { state.eventSink(AddDetailsEvents.OpenAvatarPickerSheet) },
                )
            }
        },
    )

    MediaPickerBottomSheet(
        visible = state.showAvatarSheet,
        onDismiss = { state.eventSink(AddDetailsEvents.DismissAvatarPickerSheet) },
        onClear = { state.eventSink(AddDetailsEvents.ClearAvatar) },
        onCamera = { state.eventSink(AddDetailsEvents.SelectAvatarFromCamera) },
        onGallery = { state.eventSink(AddDetailsEvents.SelectAvatarFromGallery) },
        showCamera = state.showCamera,
    )

    MediaPickerBottomSheet(
        visible = state.showCoverSheet,
        onDismiss = { state.eventSink(AddDetailsEvents.DismissCoverPickerSheet) },
        onClear = { state.eventSink(AddDetailsEvents.ClearCover) },
        onCamera = { state.eventSink(AddDetailsEvents.SelectCoverFromCamera) },
        onGallery = { state.eventSink(AddDetailsEvents.SelectCoverFromGallery) },
        showCamera = state.showCamera,
    )

    state.avatarCropper.cropper.cropState?.let {
        ImageCropperDialog(
            state = it,
            style = cropperStyle(aspects = listOf(AspectRatio(1, 1))),
        )
    }

    state.coverCropper.cropper.cropState?.let {
        ImageCropperDialog(
            state = it,
            style = cropperStyle(aspects = listOf(AspectRatio(16, 9))),
        )
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewAddDetailsUI(
    @PreviewParameter(AddDetailsStateProvider::class) state: AddDetailsState,
) = KafkaPreview {
    AddDetailsUI(state = state)
}
