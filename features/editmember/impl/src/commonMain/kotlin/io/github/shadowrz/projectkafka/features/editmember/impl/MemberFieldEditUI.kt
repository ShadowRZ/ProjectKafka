package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.attafitamim.krop.core.crop.AspectRatio
import com.attafitamim.krop.core.crop.cropperStyle
import com.attafitamim.krop.ui.ImageCropperDialog
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.CircularProgressIndicator
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.IconButton
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TextButton
import io.github.shadowrz.projectkafka.designsystem.TextField
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.adaptive.WINDOW_SIZE_CLASS_MEDIUM_LOWER_BOUND
import io.github.shadowrz.projectkafka.designsystem.icons.Check
import io.github.shadowrz.projectkafka.designsystem.icons.DeleteOutline
import io.github.shadowrz.projectkafka.designsystem.icons.ShieldOutline
import io.github.shadowrz.projectkafka.designsystem.preferences.SwitchPreference
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.kafkaui.AvatarPicker
import io.github.shadowrz.projectkafka.libraries.kafkaui.MediaPickerBottomSheet
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_cancel
import io.github.shadowrz.projectkafka.libraries.strings.common_delete
import io.github.shadowrz.projectkafka.libraries.strings.common_discard
import io.github.shadowrz.projectkafka.libraries.strings.common_ok
import io.github.shadowrz.projectkafka.libraries.strings.common_unsaved_changes_confirm_exit
import io.github.shadowrz.projectkafka.libraries.strings.member_admin
import io.github.shadowrz.projectkafka.libraries.strings.member_description
import io.github.shadowrz.projectkafka.libraries.strings.member_name
import io.github.shadowrz.projectkafka.libraries.strings.member_preferences
import io.github.shadowrz.projectkafka.libraries.strings.member_roles
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.editmember.impl.generated.resources.Res
import projectkafka.features.editmember.impl.generated.resources.editmember_delete_confirm
import projectkafka.features.editmember.impl.generated.resources.editmember_member_name_cant_empty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MemberFieldEditUI(
    state: MemberFieldEditState,
    title: String,
    modifier: Modifier = Modifier,
    supportDeleteMember: Boolean = false,
    onDeleteMember: () -> Unit = {},
) {
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                titleStr = title,
                navigationIcon = {
                    BackButton(
                        onClick = {
                            state.eventSink(MemberFieldEditEvents.Back)
                        },
                    )
                },
                actions = {
                    if (supportDeleteMember) {
                        IconButton(
                            onClick = { showDeleteDialog = true },
                        ) {
                            Icon(
                                KafkaIcons.DeleteOutline,
                                tint = Color.Red,
                                contentDescription = stringResource(CommonStrings.common_delete),
                            )
                        }
                    }
                    IconButton(
                        enabled = state.dirty && state.valid,
                        onClick = {
                            state.eventSink(MemberFieldEditEvents.Save)
                        },
                    ) {
                        if (state.saving) {
                            CircularProgressIndicator()
                        } else {
                            Icon(
                                KafkaIcons.Check,
                                contentDescription = stringResource(CommonStrings.common_ok),
                            )
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier.padding(innerPadding),
        ) {
            if (maxWidth >= WINDOW_SIZE_CLASS_MEDIUM_LOWER_BOUND.dp) {
                TwoPaneUI(
                    avatarPane = {
                        selectAvatar(
                            state,
                            Modifier.fillMaxSize().wrapContentSize(),
                        )
                    },
                    detailPane = {
                        detailPane(
                            state,
                            Modifier
                                .verticalScroll(rememberScrollState())
                                .imePadding(),
                        )
                    },
                )
            } else {
                SinglePaneUI(
                    avatarPane = {
                        selectAvatar(
                            state,
                            Modifier.padding(vertical = 32.dp),
                        )
                    },
                    detailPane = {
                        detailPane(
                            state,
                            Modifier,
                        )
                    },
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            text = {
                Text(
                    stringResource(Res.string.editmember_delete_confirm),
                )
            },
            confirmButton = {
                TextButton(
                    destructive = true,
                    text = stringResource(CommonStrings.common_delete),
                    onClick = {
                        showDeleteDialog = false
                        onDeleteMember()
                    },
                )
            },
            dismissButton = {
                TextButton(
                    text = stringResource(CommonStrings.common_cancel),
                    onClick = { showDeleteDialog = false },
                )
            },
            onDismissRequest = { showDeleteDialog = false },
        )
    }

    if (state.showDirtyDialog) {
        AlertDialog(
            text = {
                Text(
                    stringResource(CommonStrings.common_unsaved_changes_confirm_exit),
                )
            },
            confirmButton = {
                TextButton(
                    text = stringResource(CommonStrings.common_discard),
                    destructive = true,
                    onClick = {
                        state.eventSink(MemberFieldEditEvents.DiscardChanges)
                    },
                )
            },
            dismissButton = {
                TextButton(
                    text = stringResource(CommonStrings.common_cancel),
                    onClick = {
                        state.eventSink(MemberFieldEditEvents.CloseDirtyDialog)
                    },
                )
            },
            onDismissRequest = {
                state.eventSink(MemberFieldEditEvents.CloseDirtyDialog)
            },
        )
    }

    MediaPickerBottomSheet(
        visible = state.showAvatarSheet,
        onDismiss = { state.eventSink(MemberFieldEditEvents.DismissAvatarPickerSheet) },
        onClear = { state.eventSink(MemberFieldEditEvents.ClearAvatar) },
        onCamera = { state.eventSink(MemberFieldEditEvents.SelectAvatarFromCamera) },
        onGallery = { state.eventSink(MemberFieldEditEvents.SelectAvatarFromGallery) },
    )

    state.avatarCropper.cropper.cropState?.let {
        ImageCropperDialog(
            state = it,
            style = cropperStyle(aspects = listOf(AspectRatio(1, 1))),
        )
    }
}

@Composable
private fun SinglePaneUI(
    avatarPane: @Composable () -> Unit,
    detailPane: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()).imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        avatarPane()
        detailPane()
    }
}

@Composable
private fun TwoPaneUI(
    avatarPane: @Composable () -> Unit,
    detailPane: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            avatarPane()
        }
        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            detailPane()
        }
    }
}

private val selectAvatar =
    movableContentOf<MemberFieldEditState, Modifier> { state, modifier ->
        AvatarPicker(
            state = state.avatar,
            modifier = modifier,
            onClick = { state.eventSink(MemberFieldEditEvents.OpenAvatarPickerSheet) },
        )
    }

private val detailPane =
    movableContentOf<MemberFieldEditState, Modifier> { state, modifier ->
        DetailSection(
            state = state,
            modifier = modifier,
        )
    }

@Composable
private fun DetailSection(
    state: MemberFieldEditState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TextField(
            state = state.name,
            isError = !state.valid,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = stringResource(CommonStrings.member_name),
            supportingText = if (!state.valid) {
                stringResource(Res.string.editmember_member_name_cant_empty)
            } else {
                null
            },
            lineLimits = TextFieldLineLimits.SingleLine,
        )
        TextField(
            state = state.description,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = stringResource(CommonStrings.member_description),
            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 3, maxHeightInLines = 5),
        )
        TextField(
            state = state.preferences,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = stringResource(CommonStrings.member_preferences),
            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 3, maxHeightInLines = 5),
        )
        TextField(
            state = state.roles,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = stringResource(CommonStrings.member_roles),
            lineLimits = TextFieldLineLimits.SingleLine,
        )
//        DatePickerField(
//            value = state.birth,
//            onValueChange = { state.eventSink(MemberFieldEditEvents.ChangeBirth(it)) },
//            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
//            label = {
//                Text(
//                    stringResource(CommonStrings.member_birth),
//                )
//            },
//            supportingText = {},
//        )
        SwitchPreference(
            checked = state.admin,
            onCheckedChange = { state.eventSink(MemberFieldEditEvents.ChangeAdmin(it)) },
            headlineContent = {
                Text(
                    stringResource(CommonStrings.member_admin),
                )
            },
            leadingContent = {
                Icon(
                    KafkaIcons.ShieldOutline,
                    contentDescription = stringResource(CommonStrings.member_admin),
                )
            },
        )
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewMemberFieldEditUI(
    @PreviewParameter(MemberFieldEditStateProvider::class) state: MemberFieldEditState,
) = KafkaPreview {
    MemberFieldEditUI(
        title = "Edit Members",
        state = state,
    )
}
