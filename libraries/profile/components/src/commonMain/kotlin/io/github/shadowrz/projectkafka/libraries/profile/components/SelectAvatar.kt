package io.github.shadowrz.projectkafka.libraries.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperState

@Composable
expect fun SelectAvatar(
    state: CropperState,
    modifier: Modifier = Modifier,
    size: Dp = 160.dp,
)
