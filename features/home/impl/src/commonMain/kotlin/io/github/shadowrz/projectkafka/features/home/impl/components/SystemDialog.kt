package io.github.shadowrz.projectkafka.features.home.impl.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composeunstyled.DialogState
import com.composeunstyled.UnstyledDialog
import com.composeunstyled.UnstyledDialogPanel
import com.composeunstyled.UnstyledScrim
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.Cover
import io.github.shadowrz.projectkafka.designsystem.HorizontalDivider
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.IconButton
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.ListItem
import io.github.shadowrz.projectkafka.designsystem.PlainTooltip
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TooltipAnchorPosition
import io.github.shadowrz.projectkafka.designsystem.TooltipBox
import io.github.shadowrz.projectkafka.designsystem.adaptive.adaptiveValue
import io.github.shadowrz.projectkafka.designsystem.icons.DatabaseOutline
import io.github.shadowrz.projectkafka.designsystem.icons.HelpOutline
import io.github.shadowrz.projectkafka.designsystem.icons.InfoOutline
import io.github.shadowrz.projectkafka.designsystem.icons.SettingsOutline
import io.github.shadowrz.projectkafka.designsystem.icons.SwapVert
import io.github.shadowrz.projectkafka.designsystem.rememberTooltipPositionProvider
import io.github.shadowrz.projectkafka.designsystem.rememberTooltipState
import io.github.shadowrz.projectkafka.features.home.impl.HomeEvents
import io.github.shadowrz.projectkafka.features.home.impl.HomeState
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_about
import io.github.shadowrz.projectkafka.libraries.strings.common_data_management
import io.github.shadowrz.projectkafka.libraries.strings.common_help
import io.github.shadowrz.projectkafka.libraries.strings.common_settings
import io.github.shadowrz.projectkafka.libraries.strings.common_switch_system
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SystemDialog(
    state: HomeState,
    dialogState: DialogState,
) {
    val transformOrigin = adaptiveValue(
        compact = TransformOrigin(pivotFractionX = 1.0f, pivotFractionY = 0.0f),
        medium = TransformOrigin(pivotFractionX = 0.0f, pivotFractionY = 1.0f),
    )

    val offsetX = adaptiveValue(
        compact = 0.dp,
        medium = 64.dp,
    )
    val offsetY = adaptiveValue(
        compact = 52.dp,
        medium = 0.dp,
    )

    val alignment = adaptiveValue(
        compact = Alignment.TopCenter,
        medium = Alignment.BottomStart,
    )

    UnstyledDialog(
        state = dialogState,
        onDismiss = {
            state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.Closed))
        },
    ) {
        SystemDialogBackHandler(enabled = state.dialogState.visible) {
            state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.Closed))
        }

        UnstyledScrim(
            enter = fadeIn(animationSpec = tween(durationMillis = 150)),
            exit = fadeOut(animationSpec = tween(durationMillis = 150)),
        )
        Box(
            modifier =
                Modifier
                    .displayCutoutPadding()
                    .systemBarsPadding()
                    .fillMaxSize()
                    .wrapContentSize(alignment)
                    .offset(x = offsetX, y = offsetY),
        ) {
            UnstyledDialogPanel(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .widthIn(min = 240.dp, max = 360.dp)
                        .clip(AlertDialogDefaults.shape)
                        .background(AlertDialogDefaults.containerColor)
                        .verticalScroll(rememberScrollState()),
                enter =
                    fadeIn(animationSpec = tween(durationMillis = 150)) +
                        scaleIn(
                            animationSpec = tween(durationMillis = 150),
                            initialScale = .95f,
                            transformOrigin = transformOrigin,
                        ),
                exit = fadeOut(animationSpec = tween(durationMillis = 150)),
                contentColor = AlertDialogDefaults.textContentColor,
            ) {
                UpdateSystemBars()

                Column {
                    Box {
                        Cover(
                            cover = state.system.cover,
                        )
                        ListItem(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            headlineContent = {
                                Text(
                                    text = state.system.name,
                                    color = KafkaTheme.materialColors.primary,
                                    fontWeight = FontWeight.Bold,
                                )
                            },
                            supportingContent = {
                                state.system.description?.let {
                                    Text(
                                        text = it,
                                        maxLines = 1,
                                    )
                                }
                            },
                            leadingContent = {
                                Avatar(
                                    avatar = state.system.avatar,
                                    modifier = Modifier.size(40.dp),
                                )
                            },
                            trailingContent = {
                                if (state.allowsMultiSystem) {
                                    TooltipBox(
                                        positionProvider = rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
                                        tooltip = { PlainTooltip(text = stringResource(CommonStrings.common_switch_system)) },
                                        state = rememberTooltipState(),
                                    ) {
                                        IconButton(
                                            onClick = {
                                                state.eventSink(HomeEvents.OpenSwitchSystem)
                                            },
                                        ) {
                                            Icon(
                                                KafkaIcons.SwapVert,
                                                contentDescription = null,
                                            )
                                        }
                                    }
                                }
                            },
                        )
                    }
                    HorizontalDivider()
                    ListItem(
                        onClick = {
                            state.eventSink(HomeEvents.OpenSettings)
                        },
                        headlineContent = {
                            Text(
                                stringResource(CommonStrings.common_settings),
                            )
                        },
                        leadingContent = {
                            Icon(
                                KafkaIcons.SettingsOutline,
                                contentDescription = null,
                            )
                        },
                    )
                    ListItem(
                        onClick = {
                            state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.Help))
                        },
                        headlineContent = {
                            Text(
                                stringResource(CommonStrings.common_help),
                            )
                        },
                        leadingContent = {
                            Icon(
                                KafkaIcons.HelpOutline,
                                contentDescription = null,
                            )
                        },
                    )
                    ListItem(
                        onClick = {
                            state.eventSink(HomeEvents.OpenDataManage)
                        },
                        headlineContent = {
                            Text(
                                stringResource(CommonStrings.common_data_management),
                            )
                        },
                        leadingContent = {
                            Icon(
                                KafkaIcons.DatabaseOutline,
                                contentDescription = null,
                            )
                        },
                    )
                    ListItem(
                        onClick = {
                            state.eventSink(HomeEvents.OpenAbout)
                        },
                        headlineContent = {
                            Text(
                                stringResource(CommonStrings.common_about),
                            )
                        },
                        leadingContent = {
                            Icon(
                                KafkaIcons.InfoOutline,
                                contentDescription = null,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
internal expect fun UpdateSystemBars()

@Composable
internal expect fun SystemDialogBackHandler(
    enabled: Boolean,
    onBack: () -> Unit,
)
