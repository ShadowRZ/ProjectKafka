package io.github.shadowrz.projectkafka.libraries.localepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import io.github.shadowrz.projectkafka.libraries.androidutils.extensions.toList
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_cancel
import io.github.shadowrz.projectkafka.libraries.strings.common_ok
import org.jetbrains.compose.resources.stringResource
import projectkafka.libraries.localepicker.generated.resources.Res
import projectkafka.libraries.localepicker.generated.resources.localepicker_dialog_title
import projectkafka.libraries.localepicker.generated.resources.localepicker_system_default
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocaleDialog(
    applicationLocale: LocaleListCompat,
    supportedLocales: LocaleListCompat,
    modifier: Modifier = Modifier,
    onConfirm: (Locale?) -> Unit = { },
    onDismissRequest: () -> Unit = { },
) {
    var locale by remember { mutableStateOf(applicationLocale[0]) }

    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            shape = AlertDialogDefaults.shape,
            color = AlertDialogDefaults.containerColor,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp),
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides AlertDialogDefaults.titleContentColor,
                    LocalTextStyle provides MaterialTheme.typography.headlineSmall,
                ) {
                    Box(
                        modifier =
                            Modifier
                                .padding(bottom = 16.dp)
                                .padding(horizontal = 24.dp)
                                .align(Alignment.Start),
                    ) {
                        Text(
                            stringResource(Res.string.localepicker_dialog_title),
                        )
                    }
                }
                CompositionLocalProvider(
                    LocalContentColor provides AlertDialogDefaults.textContentColor,
                    LocalTextStyle provides MaterialTheme.typography.bodyMedium,
                ) {
                    Column(
                        modifier =
                            Modifier
                                .weight(
                                    weight = 1f,
                                    fill = false,
                                ).fillMaxWidth()
                                .align(Alignment.Start)
                                .selectableGroup(),
                    ) {
                        ListItem(
                            colors = ListItemDefaults.colors(
                                containerColor = Color.Transparent,
                            ),
                            modifier =
                                Modifier
                                    .clickable {
                                        locale = null
                                    },
                            leadingContent = {
                                RadioButton(
                                    selected = locale == null,
                                    onClick = {
                                        locale = null
                                    },
                                )
                            },
                            headlineContent = {
                                Text(
                                    stringResource(Res.string.localepicker_system_default),
                                )
                            },
                        )
                        supportedLocales.toList().forEach {
                            ListItem(
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent,
                                ),
                                modifier =
                                    Modifier
                                        .clickable {
                                            locale = it
                                        },
                                leadingContent = {
                                    RadioButton(
                                        selected = locale == it,
                                        onClick = {
                                            locale = it
                                        },
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        it.getDisplayName(it),
                                    )
                                },
                            )
                        }
                    }
                }
                Box(modifier = Modifier.align(Alignment.End).padding(horizontal = 24.dp)) {
                    CompositionLocalProvider(
                        LocalContentColor provides MaterialTheme.colorScheme.primary,
                        LocalTextStyle provides MaterialTheme.typography.labelLarge,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            TextButton(onClick = { onDismissRequest() }) {
                                Text(
                                    stringResource(CommonStrings.common_cancel),
                                )
                            }
                            TextButton(onClick = { onConfirm(locale) }) {
                                Text(
                                    stringResource(CommonStrings.common_ok),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
