package io.github.shadowrz.projectkafka.features.fronterindicator.impl

import android.app.PictureInPictureParams
import android.os.Build
import android.util.Rational
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.icons.Close
import io.github.shadowrz.projectkafka.designsystem.icons.PipOutline
import io.github.shadowrz.projectkafka.designsystem.icons.SettingsOutline
import io.github.shadowrz.projectkafka.designsystem.icons.SwapVert

@Composable
internal fun FronterIndicator(modifier: Modifier = Modifier) {
    var controls by rememberSaveable { mutableStateOf(false) }
    val activity = requireNotNull(LocalActivity.current)

    Box(
        modifier =
            modifier.fillMaxSize().combinedClickable(
                interactionSource = null,
                indication = null,
                onClick = {},
                onLongClick = {
                    controls = true
                },
            ),
    ) {
        AnimatedVisibility(
            visible = controls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier =
                Modifier.clickable(
                    interactionSource = null,
                    indication = null,
                ) {
                    controls = false
                },
        ) {
            Row(
                modifier = Modifier.fillMaxSize().wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilledTonalIconButton(
                    onClick = {
                        controls = false
                        activity.finish()
                    },
                    colors =
                        IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = KafkaTheme.materialColors.secondaryContainer.copy(alpha = 0.5f),
                            contentColor = KafkaTheme.materialColors.onSecondaryContainer,
                        ),
                ) {
                    Icon(
                        KafkaIcons.Close,
                        contentDescription = null,
                    )
                }
                FilledTonalIconButton(
                    onClick = {
                    },
                    colors =
                        IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = KafkaTheme.materialColors.secondaryContainer.copy(alpha = 0.5f),
                            contentColor = KafkaTheme.materialColors.onSecondaryContainer,
                        ),
                ) {
                    Icon(
                        KafkaIcons.SettingsOutline,
                        contentDescription = null,
                    )
                }
                FilledTonalIconButton(
                    onClick = {
                    },
                    colors =
                        IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = KafkaTheme.materialColors.secondaryContainer.copy(alpha = 0.5f),
                            contentColor = KafkaTheme.materialColors.onSecondaryContainer,
                        ),
                ) {
                    Icon(
                        KafkaIcons.SwapVert,
                        contentDescription = null,
                    )
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    FilledTonalIconButton(
                        onClick = {
                            controls = false
                            val pipParams = PictureInPictureParams.Builder()
                            pipParams.setAspectRatio(Rational(1, 1))
                            activity.enterPictureInPictureMode(pipParams.build())
                        },
                        colors =
                            IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = KafkaTheme.materialColors.secondaryContainer.copy(alpha = 0.5f),
                                contentColor = KafkaTheme.materialColors.onSecondaryContainer,
                            ),
                    ) {
                        Icon(
                            KafkaIcons.PipOutline,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}
