package io.github.shadowrz.projectkafka.features.home.impl.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.window.core.layout.WindowSizeClass
import com.composables.core.Dialog
import com.composables.core.DialogPanel
import com.composables.core.DialogState
import com.composables.core.Scrim
import com.composeunstyled.LocalModalWindow
import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.features.home.impl.R
import io.github.shadowrz.projectkafka.libraries.components.Avatar
import io.github.shadowrz.projectkafka.libraries.components.Cover
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.DatabaseOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.HelpOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.InfoOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.SettingsOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.SwapVert
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SystemDialog(
    state: DialogState,
    name: String,
    description: String?,
    avatar: Uri?,
    onHelp: () -> Unit = {},
    onSettings: () -> Unit = {},
    onDataManage: () -> Unit = {},
    onAbout: () -> Unit = {},
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val useNavigationRail = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    val transformOrigin =
        if (useNavigationRail) {
            TransformOrigin(pivotFractionX = 0.0f, pivotFractionY = 1.0f)
        } else {
            TransformOrigin(pivotFractionX = 1.0f, pivotFractionY = 0.0f)
        }

    val offsetX = if (useNavigationRail) 64.dp else 0.dp
    val offsetY = if (useNavigationRail) 0.dp else 52.dp

    val alignment = if (useNavigationRail) Alignment.BottomStart else Alignment.TopCenter

    Dialog(state = state) {
        Scrim(
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
            DialogPanel(
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

                val colors =
                    ListItemDefaults.colors().copy(
                        containerColor = Color.Transparent,
                    )

                Column {
                    Box {
                        Cover(
                            cover = null,
                        )
                        ListItem(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            colors = colors,
                            headlineContent = {
                                Text(
                                    text = name,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                )
                            },
                            supportingContent = {
                                description?.let {
                                    Text(
                                        text = it,
                                        maxLines = 1,
                                    )
                                }
                            },
                            leadingContent = {
                                Avatar(
                                    avatar = avatar,
                                    modifier = Modifier.size(40.dp),
                                )
                            },
                            trailingContent = {
                                TooltipBox(
                                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
                                    tooltip = { PlainTooltip { Text(stringResource(R.string.switch_tooltip)) } },
                                    state = rememberTooltipState(),
                                ) {
                                    IconButton(
                                        onClick = {},
                                    ) {
                                        Icon(
                                            MaterialIcons.SwapVert,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            },
                        )
                    }
                    HorizontalDivider()
                    ListItem(
                        colors = colors,
                        modifier = Modifier.clickable { onSettings() },
                        headlineContent = {
                            Text(
                                stringResource(CommonStrings.common_settings),
                            )
                        },
                        leadingContent = {
                            Icon(
                                MaterialIcons.SettingsOutline,
                                contentDescription = null,
                            )
                        },
                    )
                    ListItem(
                        colors = colors,
                        modifier = Modifier.clickable { onHelp() },
                        headlineContent = {
                            Text(
                                stringResource(CommonStrings.common_help),
                            )
                        },
                        leadingContent = {
                            Icon(
                                MaterialIcons.HelpOutline,
                                contentDescription = null,
                            )
                        },
                    )
                    ListItem(
                        colors = colors,
                        modifier = Modifier.clickable { onDataManage() },
                        headlineContent = {
                            Text(
                                stringResource(CommonStrings.common_data_management),
                            )
                        },
                        leadingContent = {
                            Icon(
                                MaterialIcons.DatabaseOutline,
                                contentDescription = null,
                            )
                        },
                    )
                    ListItem(
                        colors = colors,
                        modifier = Modifier.clickable { onAbout() },
                        headlineContent = {
                            Text(
                                stringResource(CommonStrings.common_about),
                            )
                        },
                        leadingContent = {
                            Icon(
                                MaterialIcons.InfoOutline,
                                contentDescription = null,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
private fun UpdateSystemBars() {
    val window = LocalModalWindow.current
    LaunchedEffect(Unit) {
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = false
        windowInsetsController.isAppearanceLightNavigationBars = false
    }
}
