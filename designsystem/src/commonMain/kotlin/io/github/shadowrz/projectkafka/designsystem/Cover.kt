package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun Cover(
    cover: String?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = cover,
        contentDescription = null,
        modifier = modifier.aspectRatio(16f / 9f),
    )
}
