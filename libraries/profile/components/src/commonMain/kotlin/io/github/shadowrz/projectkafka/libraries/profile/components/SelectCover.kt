package io.github.shadowrz.projectkafka.libraries.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectImageState

@Composable
expect fun SelectCover(
    state: SelectImageState,
    modifier: Modifier = Modifier,
)
