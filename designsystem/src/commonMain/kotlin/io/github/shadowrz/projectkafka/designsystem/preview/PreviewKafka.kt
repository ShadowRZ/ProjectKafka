package io.github.shadowrz.projectkafka.designsystem.preview

import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Preview(name = "Light")
@Preview(name = "Dark", uiMode = AndroidUiModes.UI_MODE_NIGHT_YES or AndroidUiModes.UI_MODE_TYPE_NORMAL)
@Preview(name = "Light - Red", wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE)
@Preview(
    name = "Dark - Red",
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES or AndroidUiModes.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
)
annotation class PreviewKafka
