package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.components.DatePickerField
import io.github.shadowrz.projectkafka.libraries.components.RequiredField
import io.github.shadowrz.projectkafka.libraries.components.WINDOW_SIZE_CLASS_MEDIUM_LOWER_BOUND
import io.github.shadowrz.projectkafka.libraries.components.preferences.SwitchPreference
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Check
import io.github.shadowrz.projectkafka.libraries.icons.material.Close
import io.github.shadowrz.projectkafka.libraries.icons.material.DeleteOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.ShieldOutline
import io.github.shadowrz.projectkafka.libraries.profile.components.SelectAvatar
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_cancel
import io.github.shadowrz.projectkafka.libraries.strings.common_delete
import io.github.shadowrz.projectkafka.libraries.strings.common_discard
import io.github.shadowrz.projectkafka.libraries.strings.common_ok
import io.github.shadowrz.projectkafka.libraries.strings.common_unsaved_changes_confirm_exit
import io.github.shadowrz.projectkafka.libraries.strings.member_admin
import io.github.shadowrz.projectkafka.libraries.strings.member_birth
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
                colors =
                    topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {
                    Text(
                        title,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            state.eventSink(MemberFieldEditEvents.Back)
                        },
                    ) {
                        Icon(
                            MaterialIcons.Close,
                            contentDescription = stringResource(CommonStrings.common_cancel),
                        )
                    }
                },
                actions = {
                    if (supportDeleteMember) {
                        IconButton(
                            onClick = { showDeleteDialog = true },
                        ) {
                            Icon(
                                MaterialIcons.DeleteOutline,
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
                                MaterialIcons.Check,
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
                    onClick = {
                        showDeleteDialog = false
                        onDeleteMember()
                    },
                    colors =
                        ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error,
                        ),
                ) {
                    Text(
                        stringResource(CommonStrings.common_delete),
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false },
                ) {
                    Text(
                        stringResource(CommonStrings.common_cancel),
                    )
                }
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
                    onClick = {
                        state.eventSink(MemberFieldEditEvents.DiscardChanges)
                    },
                    colors =
                        ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error,
                        ),
                ) {
                    Text(
                        stringResource(CommonStrings.common_discard),
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        state.eventSink(MemberFieldEditEvents.CloseDirtyDialog)
                    },
                ) {
                    Text(
                        stringResource(CommonStrings.common_cancel),
                    )
                }
            },
            onDismissRequest = {
                state.eventSink(MemberFieldEditEvents.CloseDirtyDialog)
            },
        )
    }

    BackHandler(enabled = state.dirty) {
        state.eventSink(MemberFieldEditEvents.Back)
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
        SelectAvatar(
            state = state.avatar,
            modifier = modifier,
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
        OutlinedTextField(
            state = state.name,
            isError = !state.valid,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = {
                Row {
                    Text(
                        stringResource(CommonStrings.member_name),
                    )
                    RequiredField()
                }
            },
            supportingText = {
                if (!state.valid) {
                    Text(
                        stringResource(Res.string.editmember_member_name_cant_empty),
                    )
                }
            },
            lineLimits = TextFieldLineLimits.SingleLine,
        )
        OutlinedTextField(
            state = state.description,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = {
                Text(
                    stringResource(CommonStrings.member_description),
                )
            },
            supportingText = {},
            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 3, maxHeightInLines = 5),
        )
        OutlinedTextField(
            state = state.preferences,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = {
                Text(
                    stringResource(CommonStrings.member_preferences),
                )
            },
            supportingText = {},
            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 3, maxHeightInLines = 5),
        )
        OutlinedTextField(
            state = state.roles,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = {
                Text(
                    stringResource(CommonStrings.member_roles),
                )
            },
            supportingText = {},
            lineLimits = TextFieldLineLimits.SingleLine,
        )
        DatePickerField(
            value = state.birth,
            onValueChange = { state.eventSink(MemberFieldEditEvents.ChangeBirth(it)) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = {
                Text(
                    stringResource(CommonStrings.member_birth),
                )
            },
            supportingText = {},
        )
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
                    MaterialIcons.ShieldOutline,
                    contentDescription = stringResource(CommonStrings.member_admin),
                )
            },
        )
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun PreviewMemberFieldEditUI(
    @PreviewParameter(MemberFieldEditStateProvider::class) state: MemberFieldEditState,
) {
    ProjectKafkaPreview {
        MemberFieldEditUI(
            title = "Edit Members",
            state = state,
        )
    }
}
