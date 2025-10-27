package io.github.shadowrz.projectkafka.libraries.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.attafitamim.krop.core.crop.AspectRatio
import com.attafitamim.krop.core.crop.cropperStyle
import io.github.shadowrz.projectkafka.libraries.components.Cover
import io.github.shadowrz.projectkafka.libraries.crop.ImageCropperDialog
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.CameraOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.Close
import io.github.shadowrz.projectkafka.libraries.icons.material.ImageOutline
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperState

@Composable
actual fun SelectCover(
    state: CropperState,
    modifier: Modifier,
) {
    Box(modifier = modifier) {
        Cover(cover = state.value)
        Row(
            modifier = Modifier.align(Alignment.BottomEnd).padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            IconButton(
                onClick = { state.clear() },
                colors =
                    IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Red.copy(alpha = 0.75F),
                        contentColor = Color.White,
                    ),
            ) {
                Icon(
                    imageVector = MaterialIcons.Close,
                    contentDescription = stringResource(R.string.profile_clear_image),
                )
            }
            IconButton(
                onClick = { state.fromGallery() },
                colors =
                    IconButtonDefaults.iconButtonColors(
                        containerColor =
                            MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                    ),
            ) {
                Icon(
                    imageVector = MaterialIcons.ImageOutline,
                    contentDescription =
                        stringResource(
                            R.string.profile_select_from_gallery,
                        ),
                )
            }
            IconButton(
                onClick = { state.fromCamera() },
                colors =
                    IconButtonDefaults.iconButtonColors(
                        containerColor =
                            MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                    ),
            ) {
                Icon(
                    imageVector = MaterialIcons.CameraOutline,
                    contentDescription =
                        stringResource(
                            R.string.profile_capture_from_camera,
                        ),
                )
            }
        }
    }

    state.imageCropper.cropState?.let {
        ImageCropperDialog(
            state = it,
            style =
                cropperStyle(aspects = listOf(AspectRatio(16, 9))),
        )
    }
}
