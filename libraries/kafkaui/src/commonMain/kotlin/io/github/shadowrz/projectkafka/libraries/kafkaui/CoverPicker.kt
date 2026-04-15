package io.github.shadowrz.projectkafka.libraries.kafkaui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.designsystem.Button
import io.github.shadowrz.projectkafka.designsystem.Cover
import io.github.shadowrz.projectkafka.designsystem.ElevatedCard
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.TextButton
import io.github.shadowrz.projectkafka.designsystem.icons.Add
import io.github.shadowrz.projectkafka.designsystem.icons.EditOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import kotlinx.serialization.Serializable

@Composable
fun CoverPicker(
    state: CoverPickerState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    ElevatedCard(modifier = modifier) {
        when (state) {
            CoverPickerState.Pick -> {
                Box(
                    modifier = Modifier
                        .clickable(onClick = onClick)
                        .aspectRatio(16 / 9f)
                        .background(KafkaTheme.materialColors.surfaceVariant),
                ) {
                    TextButton(
                        "Add Cover",
                        leadingIcon = KafkaIcons.Add,
                        onClick = onClick,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }

            is CoverPickerState.Selected -> {
                Box {
                    Cover(
                        state.value,
                        modifier = Modifier.clickable(onClick = onClick),
                    )
                    Button(
                        "Edit Cover",
                        leadingIcon = KafkaIcons.EditOutline,
                        onClick = onClick,
                        modifier = Modifier.align(Alignment.BottomEnd).padding(4.dp),
                    )
                }
            }
        }
    }
}

@Immutable
@Serializable
sealed interface CoverPickerState {
    @Serializable
    data object Pick : CoverPickerState

    @Serializable
    data class Selected(
        val value: Uri,
    ) : CoverPickerState
}

@Composable
@PreviewLightDark
internal fun PreviewCoverPicker() =
    KafkaPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CoverPicker(CoverPickerState.Pick)
            CoverPicker(CoverPickerState.Selected(value = Uri.EMPTY))
        }
    }
