package io.github.shadowrz.projectkafka.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
@NonRestartableComposable
fun Icon(
    @DrawableRes drawable: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    androidx.compose.material3.Icon(
        modifier = modifier,
        painter = painterResource(drawable),
        contentDescription = contentDescription,
        tint = tint,
    )
}
