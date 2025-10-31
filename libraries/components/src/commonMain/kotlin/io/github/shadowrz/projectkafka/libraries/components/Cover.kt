package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import coil3.compose.AsyncImage
import com.eygraber.uri.Uri

@Composable
fun Cover(
    cover: Uri?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = cover?.toString(),
        contentDescription = null,
        modifier = modifier.aspectRatio(16f / 9f),
        error = ColorPainter(Color(0xFFD9D9D9)),
    )
}
