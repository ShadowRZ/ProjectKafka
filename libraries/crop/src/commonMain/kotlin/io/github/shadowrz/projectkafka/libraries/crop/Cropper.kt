package io.github.shadowrz.projectkafka.libraries.crop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.attafitamim.krop.core.crop.CropState
import com.attafitamim.krop.core.crop.CropperStyle
import com.attafitamim.krop.core.crop.DefaultCropperStyle
import com.attafitamim.krop.core.crop.LocalCropperStyle
import com.attafitamim.krop.core.crop.flipHorizontal
import com.attafitamim.krop.core.crop.flipVertical
import com.attafitamim.krop.core.crop.rotLeft
import com.attafitamim.krop.core.crop.rotRight
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Check
import io.github.shadowrz.projectkafka.libraries.icons.material.Close
import io.github.shadowrz.projectkafka.libraries.icons.material.Flip
import io.github.shadowrz.projectkafka.libraries.icons.material.RestorePageOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.Rotate90DegreesCcwOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.Rotate90DegreesCwOutline

val CropperDialogProperties = (
    DialogProperties(
        usePlatformDefaultWidth = false,
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
    )
)

@Composable
fun ImageCropperDialog(
    state: CropState,
    style: CropperStyle = DefaultCropperStyle,
    dialogProperties: DialogProperties = CropperDialogProperties,
    dialogPadding: PaddingValues = PaddingValues(16.dp),
    dialogShape: Shape = RoundedCornerShape(8.dp),
    topBar: @Composable (CropState) -> Unit = { DefaultTopBar(it) },
    cropControls: @Composable BoxScope.(CropState) -> Unit = { DefaultControls(it) },
) {
    LaunchedEffect(Unit) {
        state.setInitialState(style) // Could be buggy, since it is run in a separate thread
    }

    CompositionLocalProvider(LocalCropperStyle provides style) {
        Dialog(
            onDismissRequest = { state.done(accept = false) },
            properties = dialogProperties,
        ) {
            Surface(
                modifier = Modifier.padding(dialogPadding),
                shape = dialogShape,
            ) {
                Column {
                    topBar(state)
                    Box(
                        modifier =
                            Modifier
                                .weight(1f)
                                .clipToBounds(),
                    ) {
                        CropperPreview(state = state, modifier = Modifier.fillMaxSize())
                        cropControls(state)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultTopBar(
    state: CropState,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                state.done(accept = false)
            }) {
                Icon(
                    MaterialIcons.Close,
                    contentDescription = null,
                )
            }
        },
        actions = {
            IconButton(onClick = {
                state.reset()
            }) {
                Icon(
                    MaterialIcons.RestorePageOutline,
                    contentDescription = null,
                )
            }
            IconButton(onClick = {
                state.done(accept = true)
            }, enabled = !state.accepted) {
                Icon(
                    MaterialIcons.Check,
                    contentDescription = null,
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun BoxScope.DefaultControls(
    state: CropState,
    modifier: Modifier = Modifier,
) {
    val useVerticalToolbar = useVerticalToolbar()

    CropUIControls(
        state = state,
        modifier =
            modifier
                .align(
                    if (useVerticalToolbar) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.BottomCenter
                    },
                ).offset(
                    y =
                        if (useVerticalToolbar) {
                            0.dp
                        } else {
                            -FloatingToolbarDefaults.ScreenOffset
                        },
                    x =
                        if (useVerticalToolbar) {
                            -FloatingToolbarDefaults.ScreenOffset
                        } else {
                            0.dp
                        },
                ),
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun CropUIControls(
    state: CropState,
    modifier: Modifier = Modifier,
) {
    val toolbar =
        remember {
            movableContentOf {
                IconButton(onClick = { state.rotLeft() }) {
                    Icon(
                        MaterialIcons.Rotate90DegreesCcwOutline,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { state.rotRight() }) {
                    Icon(
                        MaterialIcons.Rotate90DegreesCwOutline,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { state.flipHorizontal() }) {
                    Icon(
                        MaterialIcons.Flip,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { state.flipVertical() }) {
                    Icon(
                        MaterialIcons.Flip,
                        contentDescription = null,
                        modifier =
                            Modifier.rotate(
                                90f,
                            ),
                    )
                }
            }
        }

    val useVerticalToolbar = useVerticalToolbar()

    if (useVerticalToolbar) {
        VerticalFloatingToolbar(
            modifier = modifier,
            expanded = true,
        ) {
            toolbar()
        }
    } else {
        HorizontalFloatingToolbar(
            modifier = modifier,
            expanded = true,
        ) {
            toolbar()
        }
    }
}
